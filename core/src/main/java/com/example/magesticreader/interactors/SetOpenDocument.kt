package com.example.magesticreader.interactors

import com.example.magesticreader.data.DocumentRepository
import com.example.magesticreader.domain.Document

class SetOpenDocument(private val repository: DocumentRepository) {
    operator fun invoke(document: Document) = repository.setOpenDocument(document)
}