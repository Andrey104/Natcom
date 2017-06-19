package com.natcom.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.natcom.FRAGMENT_TAG
import com.natcom.LEAD_KEY
import com.natcom.R
import com.natcom.fragment.CloseLeadFragment
import com.natcom.fragment.LeadFragment
import com.natcom.model.Lead

class LeadActivity : AppCompatActivity(), LeadController {
    var lead: Lead? = null
    var fragment: Fragment? = null

    override fun lead() = lead

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lead_activity)

        val mActionBarToolbar = findViewById(R.id.toolbar_actionbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)

        lead = intent.getParcelableExtra<Lead>(LEAD_KEY)
                ?: savedInstanceState?.getParcelable(LEAD_KEY)
                ?: run {
            finish()
            return
        }

        savedInstanceState?.let {
            fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
        } ?: run {
            changeFragment(LeadFragment(), false)
        }
    }

    fun changeFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.root, fragment, FRAGMENT_TAG)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commitAllowingStateLoss()
        this.fragment = fragment
    }

    override fun closeLead() {
        val closeFragment = CloseLeadFragment()
        val bundle = Bundle()
        bundle.putParcelable(LEAD_KEY, lead)
        closeFragment.arguments = bundle
        changeFragment(closeFragment)
    }

    override fun back() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
    }
}

interface LeadController {
    fun lead(): Lead?
    fun closeLead()
    fun back()
}
