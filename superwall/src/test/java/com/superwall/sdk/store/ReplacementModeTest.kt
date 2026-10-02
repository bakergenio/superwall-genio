package com.superwall.sdk.store

import com.android.billingclient.api.BillingFlowParams.SubscriptionUpdateParams.ReplacementMode
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class ReplacementModeTest {
    private val proAnnual = BigDecimal("0.95")
    private val liteAnnual = BigDecimal("0.54")
    private val liteMonthly = BigDecimal("2.16")

    @Test
    fun same_product_is_full_price() {
        assertEquals(ReplacementMode.CHARGE_FULL_PRICE, replacementMode(true, proAnnual, liteAnnual))
    }

    @Test
    fun other_product_costing_more_per_day_charges_the_difference() {
        assertEquals(ReplacementMode.CHARGE_PRORATED_PRICE, replacementMode(false, proAnnual, liteAnnual))
    }

    @Test
    fun other_product_costing_less_per_day_is_full_price() {
        assertEquals(ReplacementMode.CHARGE_FULL_PRICE, replacementMode(false, proAnnual, liteMonthly))
    }

    @Test
    fun unknown_current_price_is_full_price() {
        assertEquals(ReplacementMode.CHARGE_FULL_PRICE, replacementMode(false, proAnnual, null))
    }
}
