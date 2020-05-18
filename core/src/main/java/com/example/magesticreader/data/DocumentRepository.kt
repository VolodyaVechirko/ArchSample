package com.example.magesticreader.data

import com.example.magesticreader.domain.Document

class DocumentRepository(
    private val documentDataSource: DocumentDataSource,
    private val openDocumentDataSource: OpenDocumentDataSource
) {
    suspend fun addDocument(document: Document) = documentDataSource.add(document)
    suspend fun removeDocument(document: Document) = documentDataSource.remove(document)
    suspend fun getDocuments() = documentDataSource.readAll()
    fun setOpenDocument(document: Document) = openDocumentDataSource.setOpenDocument(document)
    fun getOpenDocument() = openDocumentDataSource.getOpenDocument()
}