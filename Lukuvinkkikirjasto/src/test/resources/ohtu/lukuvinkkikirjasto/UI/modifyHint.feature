Feature: Käyttäjänä voin muokata vinkkejä

  Scenario: Muokkaa vinkin otsikkoa
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikkolla "test", kuvauksella "testing" ja id:llä 0
    When Käyttäjä valitsee vinkin muokkaamisen
    And Syöttää muokattavan vinkin ID:ksi 0 ja otsikoksi "test_otsikko" ja jättää muut kentät tyhjäksi
    And Varmistaa muutokset valitsemalla "y"
    Then Ohjelma tulostaa "Vinkin 0 tiedot muokattu"
    Then Vinkin 0 otsikko on "test_otsikko"
    Then Ohjelma pysähtyy
