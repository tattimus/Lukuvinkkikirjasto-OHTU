Feature: Voin merkitä vinkin luetuksi ja lukukuitaus tulostuu

  Scenario: Näytä vinkki toiminto kysyy haluanko merkitä vinkin luetuksi
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki id:llä 0, otsikolla "Teos", kuvauksella "Pakko lukea", tagilla "kirja" ja urlilla "www.tekijanKommentit.com"
    When Käyttäjä valitsee vinkin  näyttämisen ja antaa id 0
    Then Ohjelma tulostaa "Merkitäänkö luetuksi(y/n)"

  Scenario: Kun merkitsen vinkin luetuksi, tallentuu aika milloin vinkki on luettu
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki id:llä 0, otsikolla "Teos", kuvauksella "Pakko lukea", tagilla "kirja" ja urlilla "www.tekijanKommentit.com"
    When Käyttäjä valitsee vinkin  näyttämisen ja antaa id 0 ja antaa syötteen "y"
Then Vinkillä id 0 on aikaleima

  Scenario: Kun en merkitse vinkkiä luetuksi, aikaleimaa ei tule
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki id:llä 0, otsikolla "Teos", kuvauksella "Pakko lukea", tagilla "kirja" ja urlilla "www.tekijanKommentit.com"
    When Käyttäjä valitsee vinkin  näyttämisen ja antaa id 0 ja antaa syötteen "n"
Then Vinkillä id 0 ei ole aikaleimaa

  Scenario: Kun merkitsen vinkin luetuksi, lukukuittaus tulostuu näytä vinkki-näkymässä
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki id:llä 0, otsikolla "Teos", kuvauksella "Pakko lukea", tagilla "kirja" ja urlilla "www.tekijanKommentit.com"
    When Käyttäjä valitsee vinkin  näyttämisen ja antaa id 0 ja antaa syötteen "y"
And Käyttäjä valitsee vinkin  näyttämisen ja antaa id 0
Then Vinkin 0 lukukuittaus tulostuu oikein
