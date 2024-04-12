Feature: Use Shopping Cart
  @test1
  Scenario: Buying products at SwagLabs
    Given that Juan signed-in SwagLabs app
    When He puts in the cart the "Sauce Labs Backpack"
    Then He should see the btn REMOVE

