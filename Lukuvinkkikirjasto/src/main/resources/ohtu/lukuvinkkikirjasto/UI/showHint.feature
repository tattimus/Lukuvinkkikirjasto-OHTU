Feature: Käyttäjänä voin nähdä vinkin kaikki tiedot

  Scenario: käyttäjä saa vinkin id:n listauksesta
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki id:llä 0, otsikolla "Teos", kuvauksella "Pakko lukea", tagilla "kirja" ja urlilla "www.tekijanKommentit.com"
    When Käyttäjä valitsee vinkkien listauksen
    Then Ohjelma tulostaa "ID: 0"
    And Ohjelma tulostaa "Otsikko: Teos"
    Then Ohjelma pysähtyy

  Scenario: Ohjelma tulostaa vinkin kaikki tiedot
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki id:llä 0, otsikolla "Teos", kuvauksella "Pakko lukea", tagilla "kirja" ja urlilla "www.tekijanKommentit.com"
    When Käyttäjä valitsee vinkin  näyttämisen ja antaa id 0 ja antaa syötteen "n"
    Then Ohjelma tulostaa "Otsikko: Teos"
    And Ohjelma tulostaa "Kommentti: Pakko lukea"
    And Ohjelma tulostaa "URL: www.tekijanKommentit.com"
    And Ohjelma tulostaa "Tagit: kirja"
    Then Ohjelma pysähtyy

  Scenario: Ohjelma antaa virheen väärästä id:stä
    Given Ohjelma on käynnistetty
    When Käyttäjä valitsee vinkin  näyttämisen ja antaa id 70
    Then Ohjelma tulostaa "Virheellinen syöte"
    Then Ohjelma pysähtyy
