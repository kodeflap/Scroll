package kodeflap.com.scroll

import android.app.Application
import kodeflap.com.scroll.data.network.ApiInterface
import kodeflap.com.scroll.data.network.NetworkConnectionInterceptor
import kodeflap.com.scroll.data.repositories.HomeRepository
import kodeflap.com.scroll.data.repositories.UserDetailRepository
import kodeflap.com.scroll.ui.home.HomeViewModelFactory
import kodeflap.com.scroll.ui.userdetail.UserDetailViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ScrollApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ScrollApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiInterface(instance()) }
        bind() from singleton { HomeRepository(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from singleton { UserDetailRepository(instance()) }
        bind() from provider { UserDetailViewModelFactory(instance()) }


    }

}