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


