Feature: feature is key

  Scenario: Init project
    When we get "/stock"

    Then we receive exactly:
"""yml
"""
