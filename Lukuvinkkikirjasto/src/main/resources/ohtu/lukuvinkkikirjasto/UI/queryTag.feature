Feature: Käyttäjänä voin hakea vinkkejä tagin perusteella

    Scenario: Hae vinkkejä väärällä tagilla
        Given Ohjelma on käynnistetty
        When Käyttäjä valitsee tagilla hakemisen ja antaa tagin "afsfa"
        Then Ohjelma tulostaa "Tagilla ei löytynyt vinkkejä"
        Then Ohjelma pysähtyy

    Scenario: Hae vinkkejä oikealla tagilla
        Given Ohjelma on käynnistetty
        And Tietokantaan on tallennettu vinkki otsikkolla "test", kuvauksella "testing" ja tagilla "kirja"
        And Tietokantaan on tallennettu vinkki otsikkolla "oppikirja", kuvauksella "painava" ja tagilla "kirja"
        When Käyttäjä valitsee tagilla hakemisen ja antaa tagin "kirja"
        Then Ohjelma tulostaa "Tagi: kirja"
        And Ohjelma tulostaa "Otsikko: test"
        And Ohjelma tulostaa "Otsikko: oppikirja"
        Then Ohjelma pysähtyy