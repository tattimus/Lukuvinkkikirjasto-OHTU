
Feature: Lisätessäni vinkin voin halutessani määritellä sille URLin

  Scenario: Lisää vinkki URLin kanssa
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikkolla "otsikko", kuvauksella "kommentti" ja tagilla "" ja urlilla "url"
    Then Kirjastoon on lisätty vinkki, jolla on otsikkona "otsikko" ja kommenttina "kommentti" ja urlina "url"
    Then Ohjelma pysähtyy

Scenario: Lisää vinkki youtube URLin kanssa
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikkolla "otsikko", kuvauksella "kommentti" ja tagilla "" ja urlilla "youtube.com"
    Then Käyttäjä valitsee tagilla hakemisen ja antaa tagin "video"
        Then Ohjelma tulostaa "Tagi: video"
        And Ohjelma tulostaa "Otsikko: otsikko"
        Then Ohjelma pysähtyy

Scenario: Lisää vinkki youtube URLin kanssa
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki otsikkolla "otsikko", kuvauksella "kommentti" ja tagilla "" ja urlilla "dl.acm.org"
    Then Käyttäjä valitsee tagilla hakemisen ja antaa tagin "kirja"
        Then Ohjelma tulostaa "Tagi: kirja"
        And Ohjelma tulostaa "Otsikko: otsikko"
        Then Ohjelma pysähtyy


