package dojo.supermarket.moduletest

import dojo.supermarket.ReceiptPrinter
import dojo.supermarket.model.*
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SupermarketTest {

    companion object {
        val apples: Product
            get() = Product("apples", ProductUnit.Kilo)

        val toothbrush: Product
            get() = Product("toothbrush", ProductUnit.Each)

    }


    @Test
    fun testSomething() {
        val catalog = FakeCatalog()
        catalog.addProduct(toothbrush, 0.99)
        catalog.addProduct(apples, 1.99)

        val cart =
            ShoppingCart()
        cart.addItemQuantity(apples, 2.5)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0)

        val receipt = teller.checksOutArticlesFrom(cart)

        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    fun testEmptyCatalogAndCart() {
        // arrange
        var emptyCatalog = FakeCatalog()
        var emptyCart = ShoppingCart()

        val teller = Teller(emptyCatalog)

        // act
        val receipt = teller.checksOutArticlesFrom(emptyCart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun testEmptyCart() {
        // arrange
        var catalog = FakeCatalog()
        catalog.addProduct(apples, 1.99)
        var emptyCart = ShoppingCart()

        val teller = Teller(catalog)

        // act
        val receipt = teller.checksOutArticlesFrom(emptyCart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }
    @Test
    fun addProductNotInCatalog() {
        // arrange
        var emptyCatalog  = FakeCatalog()
        var cart = ShoppingCart()
        cart.addItemQuantity(apples, 0.5)

        val teller = Teller(emptyCatalog)

        // act & assert
        assertThrows<NullPointerException> {
            val receipt = teller.checksOutArticlesFrom(cart)
        }
    }

    @Test
    fun testProductWithoutDiscount() {
        // arrange
        var catalog = FakeCatalog()
        var cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 3.0)

        val teller = Teller(catalog)

        // act
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun testProductWithNegativeQuantity() {
        // arrange
        var catalog = FakeCatalog()
        var cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, -2.0)

        val teller = Teller(catalog)

        // act
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun testHalfToothbrush() {
        // arrange
        var catalog = FakeCatalog()
        var cart = ShoppingCart()
        catalog.addProduct(toothbrush, 1.99)
        cart.addItemQuantity(toothbrush, 0.5)

        val teller = Teller(catalog)

        // act
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun testNegativePrice() {
        // arrange
        var catalog = FakeCatalog()
        var cart = ShoppingCart()
        catalog.addProduct(apples, -1.99)
        cart.addItemQuantity(apples, 2.0)

        val teller = Teller(catalog)

        // act
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun threeForTwo_onlyTwo() {
        // arrange
        var catalog = FakeCatalog()
        var cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 2.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun threeForTwo_three() {
        // arrange
        var catalog = FakeCatalog()
        var cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 3.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun threeForTwo_seven() {
        // arrange
        var catalog = FakeCatalog()
        var cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 7.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

}
