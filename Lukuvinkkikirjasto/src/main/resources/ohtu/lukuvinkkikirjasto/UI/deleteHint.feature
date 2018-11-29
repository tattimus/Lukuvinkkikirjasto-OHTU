Feature: Käyttäjänä voin poistaa vinkkejä

  Scenario: Yrittää poistaa vinkkiä Id:llä
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee Vinkin poistamisen, antaa väärän ID "999"
    Then Ohjelma tulostaa "Virheellinen id"
    Then Ohjelma pysähtyy

  Scenario: Yrittää poistaa vinkkiä vääränläisellä syötetteellä
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee Vinkin poistamisen, antaa väärän ID "asdhadhk"
    Then Ohjelma tulostaa "Virheellinen syöte"
    Then Ohjelma pysähtyy

  Scenario: Olemassa olevan Vinkin poistaminen
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikkolla "test", kuvauksella "testing" ja id:llä 0
    When Käyttäjä valitsee Vinkin poistamisen, antaa ID "0" ja vahvistaa että haluaa poistaa vinkin
    Then Ohjelma tulostaa "Vinkki poistettu"
    And Kirjastosta ei enään löydy vinkkiä id:llä 0
    Then Ohjelma pysähtyy

  Scenario: Vinkin poistamisen peruminen varmistuksessa
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikkolla "test", kuvauksella "testing" ja id:llä 0
    When Käyttäjä valitsee Vinkin poistamisen, antaa ID "0" ja ei vahvista poistamista
    Then Ohjelma tulostaa "Vinkkiä ei poistettu"
    And Kirjastosta löytyy id:llä 0 vinkki jonka otsikko "test" ja kuvaus "testing"
    Then Ohjelma pysähtyy
