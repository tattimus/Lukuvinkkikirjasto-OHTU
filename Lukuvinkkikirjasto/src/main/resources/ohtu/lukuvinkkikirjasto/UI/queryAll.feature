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

  Scenario: Ohjelma tulostaa ID kaikille vinkeille
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki id:llä 0, otsikolla "Teos", kuvauksella "Pakko lukea", tagilla "kirja" ja urlilla "www.tekijanKommentit.com"
    And Tietokantaan on tallennettu vinkki id:llä 1, otsikolla "Testing for Dummies", kuvauksella "all about testing", tagilla "test" ja urlilla "www.testi.fi"
    When Käyttäjä valitsee vinkkien listauksen
    Then Ohjelma tulostaa "ID: 0"
    And Ohjelma tulostaa "Otsikko: Teos"
    And Ohjelma tulostaa "ID: 1"
    And Ohjelma tulostaa "Otsikko: Testing for Dummies"
    Then Ohjelma pysähtyy
    
  Scenario: Ohjelma tulostaa aikaleiman luetuille vinkeille
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki id:llä 0, otsikolla "Teos", kuvauksella "Pakko lukea", tagilla "kirja" ja urlilla "www.tekijanKommentit.com"
    And Käyttäjä valitsee vinkin  näyttämisen ja antaa id 0 ja antaa syötteen "y"
    When Käyttäjä valitsee vinkkien listauksen
    Then Vinkin ID 0 Aikaleima tulostuu
    Then Ohjelma pysähtyy

  Scenario: Ohjelma tulostaa aikaleiman luetuille vinkeille
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki id:llä 0, otsikolla "Teos", kuvauksella "Pakko lukea", tagilla "kirja" ja urlilla "www.tekijanKommentit.com"
    When Käyttäjä valitsee vinkkien listauksen
    Then Ohjelma tulostaa "ID: 0"
    And Ohjelma tulostaa "Otsikko: Teos"
    And Vinkin ID 0 Aikaleima ei tulostuu
    Then Ohjelma pysähtyy

    