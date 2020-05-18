package com.example.magesticreader.framework;

import android.content.Context
import com.example.magesticreader.data.DocumentDataSource
import com.example.magesticreader.domain.Document
import com.example.magesticreader.framework.db.DocumentDao
import com.example.magesticreader.framework.db.DocumentEntity
import com.example.magesticreader.framework.db.MajesticReaderDatabase

class RoomDocumentDataSource(
    val context: Context,
    val documentDao: DocumentDao
) : DocumentDataSource {

    override suspend fun add(document: Document) {
        val details = FileUtil.getDocumentDetails(context, document.url)
        documentDao.addDocument(
            DocumentEntity(document.url, details.name, details.size, details.thumbnail)
        )
    }

    override suspend fun readAll() =
        documentDao.getDocuments().map {
            Document(it.uri, it.title, it.size, it.thumbnailUri)
        }


    override suspend fun remove(document: Document) {
        documentDao.removeDocument(
            DocumentEntity(document.url, document.name, document.size, document.thumbnail)
        )
    }
}
