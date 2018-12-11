Feature: Käyttäjänä voin hakea vinkkejä vapaalla haulla

  Scenario: Voin hakea vinkkejä otsikkotiedolla
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikolla "otsikko", kuvauksella "kuvaus" ja tekijällä "tekijä"
    When Käyttäjä valitsee vapaan haun ja antaa hakusanan "otsikko"
    Then Ohjelma tulostaa "Otsikko: otsikko"
    And Ohjelma pysähtyy

  Scenario: Voin hakea vinkkejä otsikkotiedolla
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikolla "otsikko", kuvauksella "kuvaus" ja tekijällä "tekijä"
    When Käyttäjä valitsee vapaan haun ja antaa hakusanan "kuvaus"
    Then Ohjelma tulostaa "Kommentti: kuvaus"
    And Ohjelma pysähtyy

  Scenario: Voin hakea vinkkejä tekijätiedolla
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikolla "otsikko", kuvauksella "kuvaus" ja tekijällä "tekijä"
    When Käyttäjä valitsee vapaan haun ja antaa hakusanan "tekijä"
    Then Ohjelma tulostaa "tekijät: tekijä"
    And Ohjelma pysähtyy

  Scenario: Olemattomlla hakusanalla ei löytdy tuloksia
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikolla "otsikko", kuvauksella "kuvaus" ja tekijällä "tekijä"
    When Käyttäjä valitsee vapaan haun ja antaa hakusanan "olematon"
    Then Ohjelma tulostaa "Hakusanalla ei löytynyt tuloksia"
    And Ohjelma pysähtyy

  Scenario: Vinkit tulostuvat oikeassa järjestyksessä
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikolla "Ensimmäinen vinkki", kuvauksella "Tietoverkkojen perustiedot" ja tekijällä "tekijä"
    And Tietokantaan on tallennettu vinkki otsikolla "Tietokoneen toiminta", kuvauksella "kuvaus" ja tekijällä "tekijä"
    And Tietokantaan on tallennettu vinkki otsikolla "Kolmas vinkki", kuvauksella "kuvaus" ja tekijällä "Tieto Oyj"
    When Käyttäjä valitsee vapaan haun ja antaa hakusanan "tieto"
    Then tulosteessa on sana "Tietokoneen" ennen sanaa "Ensimmäinen"
    And tulosteessa on sana "Kolmas" ennen sanaa "Ensimmäinen"
    And Ohjelma pysähtyy
