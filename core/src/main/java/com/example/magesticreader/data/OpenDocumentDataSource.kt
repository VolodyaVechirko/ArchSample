package com.example.magesticreader.data

import com.example.magesticreader.domain.Document

interface OpenDocumentDataSource {

    fun setOpenDocument(document: Document)

    fun getOpenDocument(): Document
}