package dojo.supermarket

import dojo.supermarket.model.Product
import dojo.supermarket.model.ProductUnit

class TestUtil {
    companion object {
        val apples: Product
            get() = Product("apples", ProductUnit.Kilo)

        val toothbrush: Product
            get() = Product("toothbrush", ProductUnit.Each)

    }
}