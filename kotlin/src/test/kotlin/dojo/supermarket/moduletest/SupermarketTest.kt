package dojo.supermarket.moduletest

import dojo.supermarket.ReceiptPrinter
import dojo.supermarket.TestUtil.Companion.apples
import dojo.supermarket.TestUtil.Companion.toothbrush
import dojo.supermarket.model.*
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SupermarketTest {


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

        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun testEmptyCatalogAndCart() {
        // arrange
        val emptyCatalog = FakeCatalog()
        val emptyCart = ShoppingCart()

        val teller = Teller(emptyCatalog)

        // act
        val receipt = teller.checksOutArticlesFrom(emptyCart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun testEmptyCart() {
        // arrange
        val catalog = FakeCatalog()
        catalog.addProduct(apples, 1.99)
        val emptyCart = ShoppingCart()

        val teller = Teller(catalog)

        // act
        val receipt = teller.checksOutArticlesFrom(emptyCart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }
    @Test
    fun addProductNotInCatalog() {
        // arrange
        val emptyCatalog  = FakeCatalog()
        val cart = ShoppingCart()
        cart.addItemQuantity(apples, 0.5)

        val teller = Teller(emptyCatalog)

        // act & assert
        assertThrows<NullPointerException> {
            teller.checksOutArticlesFrom(cart)
        }
    }

    @Test
    fun testProductWithoutDiscount() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
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
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
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
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
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
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
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
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
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
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
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
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 7.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun addExistingProduct() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 7.0)
        val teller = Teller(catalog)

        // act
        cart.addItemQuantity(apples, 7.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun twoForAmount_One() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 1.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun twoForAmount_Two() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 2.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun twoForAmount_Seven() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 7.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun twoForAmount_NegativeAmount() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, -7.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun twoForNegativeAmount_Seven() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 7.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, apples, -2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun fiveForAmount_One() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 1.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun fiveForAmount_Five() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 5.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun fiveForAmount_Eleven() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 11.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun fiveForAmount_NegativeAmount() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, -7.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun fiveForNegativeAmount_Eleven() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 11.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, apples, -2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun tenPercentDiscount_Eleven() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 11.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun tenPercentDiscount_NegativeAmount() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, -11.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }

    @Test
    fun tenPercentDiscount_Eleven_NegativeDiscount() {
        // arrange
        val catalog = FakeCatalog()
        val cart = ShoppingCart()
        catalog.addProduct(apples, 1.99)
        cart.addItemQuantity(apples, 11.0)

        val teller = Teller(catalog)

        // act
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, -2.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        // assert
        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }
}
