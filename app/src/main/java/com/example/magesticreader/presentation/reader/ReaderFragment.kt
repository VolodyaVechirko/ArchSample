/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.example.magesticreader.presentation.reader

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.magesticreader.R
import com.example.magesticreader.domain.Document
import com.example.magesticreader.presentation.IntentUtil
import com.example.magesticreader.presentation.library.LibraryFragment
import kotlinx.android.synthetic.main.fragment_reader.*
import org.koin.android.viewmodel.ext.android.viewModel

class ReaderFragment : Fragment() {

    companion object {

        fun newInstance(document: Document) = ReaderFragment().apply {
            arguments = ReaderViewModel.createArguments(document)
        }
    }

    private val readerViewModel: ReaderViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reader, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = BookmarksAdapter {
            readerViewModel.openBookmark(it)
        }
        bookmarksRecyclerView.adapter = adapter

        readerViewModel.document.observe(viewLifecycleOwner, Observer {
            if (it == Document.EMPTY) {
                // Show file picker action.
                startActivityForResult(
                    IntentUtil.createOpenIntent(),
                    LibraryFragment.READ_REQUEST_CODE
                )
            }
        })

        readerViewModel.bookmarks.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })

        readerViewModel.isBookmarked.observe(viewLifecycleOwner, Observer {
            val bookmarkDrawable = if (it) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
            tabBookmark.setCompoundDrawablesWithIntrinsicBounds(0, bookmarkDrawable, 0, 0)
        })

        readerViewModel.isInLibrary.observe(viewLifecycleOwner, Observer {
            val libraryDrawable = if (it) R.drawable.ic_library else R.drawable.ic_library_border
            tabLibrary.setCompoundDrawablesRelativeWithIntrinsicBounds(0, libraryDrawable, 0, 0)
        })

        readerViewModel.currentPage.observe(viewLifecycleOwner, Observer {
            showPage(it)
        })
        readerViewModel.hasNextPage.observe(viewLifecycleOwner, Observer {
            tabNextPage.isEnabled = it
        })
        readerViewModel.hasPreviousPage.observe(viewLifecycleOwner, Observer {
            tabPreviousPage.isEnabled = it
        })
        readerViewModel.loadArguments(arguments)

        tabBookmark.setOnClickListener { readerViewModel.toggleBookmark() }
        tabLibrary.setOnClickListener { readerViewModel.toggleInLibrary() }
        tabNextPage.setOnClickListener { readerViewModel.nextPage() }
        tabPreviousPage.setOnClickListener { readerViewModel.previousPage() }
    }

    private fun showPage(page: PdfRenderer.Page) {
        iv_page.visibility = View.VISIBLE
        pagesTextView.visibility = View.VISIBLE
        tabPreviousPage.visibility = View.VISIBLE
        tabNextPage.visibility = View.VISIBLE

        if (iv_page.drawable != null) {
            (iv_page.drawable as BitmapDrawable).bitmap.recycle()
        }

        val size = Point()
        activity?.windowManager?.defaultDisplay?.getSize(size)

        val pageWidth = size.x
        val pageHeight = page.height * pageWidth / page.width

        val bitmap = Bitmap.createBitmap(
            pageWidth,
            pageHeight,
            Bitmap.Config.ARGB_8888
        )

        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        iv_page.setImageBitmap(bitmap)

        pagesTextView.text = getString(
            R.string.page_navigation_format,
            page.index,
            readerViewModel.renderer.value?.pageCount
        )

        page.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Process open file intent.
        if (requestCode == LibraryFragment.READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri -> readerViewModel.openDocument(uri) }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
