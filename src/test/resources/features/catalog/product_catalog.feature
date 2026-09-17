Feature: Product Catalog
  AS a customer I want to easly search, filter and sort products in the catalog

  Rule: Customers should be able to search for products by name
    Example the one where sally searches for an Adjustable Wrench
      Given Sally on the home page
      When Sally searches for an "Adjustable Wrench"
      Then the "Adjustable Wrench" product should be displayed