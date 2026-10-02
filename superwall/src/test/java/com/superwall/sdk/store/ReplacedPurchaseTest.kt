package com.superwall.sdk.store

import com.android.billingclient.api.Purchase
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ReplacedPurchaseTest {
    private fun purchase(
        product: String,
        state: Int = Purchase.PurchaseState.PURCHASED,
    ): Purchase =
        mockk {
            every { products } returns listOf(product)
            every { purchaseState } returns state
        }

    @Test
    fun same_product_wins_over_another_active_one() {
        val lite = purchase("lite")
        val pro = purchase("pro")
        assertEquals(lite, replacedPurchase(listOf(pro, lite), "lite"))
    }

    @Test
    fun single_active_other_product_is_replaced() {
        val lite = purchase("lite")
        assertEquals(lite, replacedPurchase(listOf(lite), "pro"))
    }

    @Test
    fun several_active_other_products_replace_nothing() {
        assertNull(replacedPurchase(listOf(purchase("lite"), purchase("legacy")), "pro"))
    }

    @Test
    fun pending_purchase_is_ignored() {
        assertNull(replacedPurchase(listOf(purchase("lite", Purchase.PurchaseState.PENDING)), "pro"))
    }

    @Test
    fun no_purchases_replace_nothing() {
        assertNull(replacedPurchase(emptyList(), "pro"))
    }
}
