package com.example.magesticreader.framework

import com.example.magesticreader.data.BookmarkDataSource
import com.example.magesticreader.domain.Bookmark
import com.example.magesticreader.domain.Document
import com.example.magesticreader.framework.db.BookmarkDao
import com.example.magesticreader.framework.db.BookmarkEntity

class RoomBookmarkDataSource(
    private val bookmarkDao: BookmarkDao
) : BookmarkDataSource {

    override suspend fun add(document: Document, bookmark: Bookmark) =
        bookmarkDao.addBookmark(BookmarkEntity(documentUri = document.url, page = bookmark.page))

    override suspend fun read(document: Document): List<Bookmark> =
        bookmarkDao.getBookmarks(documentUri = document.url).map { Bookmark(it.id, it.page) }

    override suspend fun remove(document: Document, bookmark: Bookmark) {
        bookmarkDao.removeBookmark(
            BookmarkEntity(id = bookmark.id, documentUri = document.url, page = bookmark.page)
        )
    }
}