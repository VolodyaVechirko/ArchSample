package com.example.magesticreader.framework

import com.example.magesticreader.data.OpenDocumentDataSource
import com.example.magesticreader.domain.Document

class InMemoryOpenDocumentDataSource : OpenDocumentDataSource {

    private var openDocument: Document = Document.EMPTY

    override fun setOpenDocument(document: Document) {
        openDocument = document
    }

    override fun getOpenDocument(): Document = openDocument
}