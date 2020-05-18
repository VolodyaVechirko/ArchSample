package com.example.magesticreader.framework.di

import com.example.magesticreader.data.*
import com.example.magesticreader.framework.InMemoryOpenDocumentDataSource
import com.example.magesticreader.framework.RoomBookmarkDataSource
import com.example.magesticreader.framework.RoomDocumentDataSource
import com.example.magesticreader.framework.db.MajesticReaderDatabase
import com.example.magesticreader.interactors.*
import com.example.magesticreader.presentation.library.LibraryViewModel
import com.example.magesticreader.presentation.reader.ReaderViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // DATABASE
    single { MajesticReaderDatabase.buildDatabase(get()) }

    // REPOSITORIES
    single { DocumentRepository(get(), get()) }
    single { BookmarkRepository(get()) }

    // INTERACTOR
    single {
        Interactors(
            AddBookmark(get()),
            GetBookmarks(get()),
            RemoveBookmark(get()),
            AddDocument(get()),
            GetDocuments(get()),
            RemoveDocument(get()),
            GetOpenDocument(get()),
            SetOpenDocument(get())
        )
    }

    // DATA_SOURCES
    single<BookmarkDataSource> {
        RoomBookmarkDataSource(get<MajesticReaderDatabase>().bookmarkDao)
    }
    single<DocumentDataSource> {
        RoomDocumentDataSource(get(), get<MajesticReaderDatabase>().documentDao)
    }
    single<OpenDocumentDataSource> {
        InMemoryOpenDocumentDataSource()
    }

    // ViewModels
    viewModel { LibraryViewModel(get()) }
    viewModel { ReaderViewModel(get(), get()) }
}