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

  Scenario: Muokkaa vinkin otsikkoa ja peru muutos
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikkolla "test", kuvauksella "testing" ja id:llä 0
    When Käyttäjä valitsee vinkin muokkaamisen
    And Syöttää muokattavan vinkin ID:ksi 0 ja otsikoksi "test_otsikko" ja jättää muut kentät tyhjäksi
    And Peruu muutokset valitsemalla "n"
    Then Ohjelma tulostaa "Vinkkiä 0 ei muokattu"
    Then Vinkin 0 otsikko on "test"
    Then Ohjelma pysähtyy

  Scenario: Muokkaa vinkin tageja
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikkolla "test", kuvauksella "testing" ja tagilla "tagi"
    When Käyttäjä valitsee vinkin muokkaamisen
    And Syöttää muokattavan vinkin ID:ksi 0 ja otsikoksi "test_otsikko" ja jättää muut kentät tyhjäksi, mutta muokkaa tageja
    And Poistaa tagin "tagi" painamalla "y"
    And Lisää uudet tagit "testi,test_tag"
    And Ei lisää uusia tekijoita
    And Varmistaa muutokset valitsemalla "y"
    Then Ohjelma tulostaa "Vinkin 0 tiedot muokattu"
    Then Vinkin 0 otsikko on "test_otsikko"
    Then Vinkillä 0 on tageina "testi,test_tag"
    Then Ohjelma pysähtyy

  Scenario: Muokka vinkin tekijöitä
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikkolla "test", kuvauksella "testing" ja tekijalla "tekija1"
    When Käyttäjä valitsee vinkin muokkaamisen
    And Syöttää muokattavan vinkin ID:ksi 0 ja otsikoksi "test_otsikko" ja jättää muut kentät tyhjäksi, mutta muokkaa tekijöitä
    And Poistaa tekijan "tekija1" painamalla "y
    And Lisää uudet tekijat "A,B"
    And Varmistaa muutokset valitsemalla "y"
    Then Ohjelma tulostaa "Vinkin 0 tiedot muokattu"
    Then Vinkin 0 otsikko on "test_otsikko"
    Then Vinkillä 0 on tekijoina "A,B"
    Then Ohjelma pysähtyy
