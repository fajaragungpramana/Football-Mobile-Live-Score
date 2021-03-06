package com.mengsoftstudio.android.footballmobile.views.fragment.tab

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mengsoftstudio.android.footballmobile.R
import com.mengsoftstudio.android.footballmobile.contracts.MatchFavoriteContract
import com.mengsoftstudio.android.footballmobile.extensions.gone
import com.mengsoftstudio.android.footballmobile.extensions.visible
import com.mengsoftstudio.android.footballmobile.models.Match
import com.mengsoftstudio.android.footballmobile.presenters.MatchFavoritePresenter
import com.mengsoftstudio.android.footballmobile.views.adapter.MatchAdapter
import com.mengsoftstudio.android.footballmobile.views.base.BaseFragment
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.android.synthetic.main.support_no_connection.view.*
import kotlinx.android.synthetic.main.tab_favorite.view.*
import javax.inject.Inject

class MatchFavoriteTab : BaseFragment(), MatchFavoriteContract.View {

    private lateinit var ui: View

    @Inject lateinit var presenter: MatchFavoritePresenter

    override fun onFragmentCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = inflater.inflate(R.layout.tab_favorite, container, false)

        RxJavaPlugins.setErrorHandler {}

        ui.btn_try_gain.setOnClickListener {
            presenter.getMatchFavorite()
        }

        return ui
    }

    override fun onStart() {
        super.onStart()

        presenter.getMatchFavorite()

    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onDestroyPresenter()

    }

    override fun onShowLoading() {
        ui.pb_favorite.visible()
        ui.rv_favorite.gone()
        ui.include_no_connection.gone()
    }

    override fun onSuccessFetchData(matchList: List<Match>) {
        ui.rv_favorite.layoutManager = LinearLayoutManager(activity)
        ui.rv_favorite.adapter = MatchAdapter(matchList, activity!!)
    }

    override fun onFailureFetchData() {
        ui.pb_favorite.gone()
        ui.rv_favorite.gone()
        ui.include_no_connection.visible()
    }

    override fun onHideLoading() {
        ui.pb_favorite.gone()
        ui.rv_favorite.visible()
        ui.include_no_connection.gone()
    }

}