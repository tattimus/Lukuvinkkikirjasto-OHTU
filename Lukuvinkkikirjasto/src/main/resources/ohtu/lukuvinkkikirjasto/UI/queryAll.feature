Feature: Käyttäjänä voin tulostaa kaikki tallenetut vinkit

    Scenario: ohjelma tulostaa kaikki tallenetut vinkit
        Given Ohjelma on käynnistetty
        And Tietokantaan on tallennettu vinkki otsikkolla "test_book", kuvauksella "testing" ja tagilla "kirja"
        And Tietokantaan on tallennettu vinkki otsikkolla "test_video", kuvauksella "hassu" ja tagilla "video"
        And Tietokantaan on tallennettu vinkki otsikkolla "test_link", kuvauksella "link" ja tagilla ""
        When Käyttäjä valitsee vinkkien listauksen
        Then Ohjelma tulostaa "test_book"
        And Ohjelma tulostaa "test_video"
        And Ohjelma tulostaa "test_link"
        Then Ohjelma pysähtyy