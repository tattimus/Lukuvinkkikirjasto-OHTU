Feature: Käyttäjänä voin lisätä vinkkejä ISBNn perusteella

  Scenario: Lisää vinkki validilla ISBN:llä
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee vinkin lisäämisen ISBN perusteella ja ISBN:ksi "9780132936330"
    And Käyttäjä syöttää vinkille kommentin "tietokoneen toiminta"
    Then Kirjastoon on lisätty vinkki, jolla on otsikkona "9780132936330" ja kommenttina "tietokoneen toiminta"
    Then Ohjelma pysähtyy

  Scenario: Lisää vinkki virheellisellä ISBN:llä
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee vinkin lisäämisen ISBN perusteella ja ISBN:ksi "978013293633"
    Then Ohjelma tulostaa "Virheellinen ISBN: 978013293633"
    Then Ohjelma pysähtyy

  Scenario: Ohjelma tulostaa virheen jos vinkin lataus epäonnistuu kun verkkoyhteyttä ei ole
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee vinkin lisäämisen ISBN perusteella ja ISBN:ksi "9780262033848"
    Then Ohjelma tulostaa "Kirjan tietojen lataaminen epäonnistui, tarkista verkkoyhteytesi."
    Then Ohjelma pysähtyy

  Scenario: Ohjelma tulostaa virheen jos ISBN:llä ei löyty vinkkiä
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee vinkin lisäämisen ISBN perusteella ja ISBN:ksi "9780133805918"
    Then Ohjelma tulostaa "ISBN:llä 9780133805918 ei löytynyt kirjaa."
    Then Ohjelma pysähtyy