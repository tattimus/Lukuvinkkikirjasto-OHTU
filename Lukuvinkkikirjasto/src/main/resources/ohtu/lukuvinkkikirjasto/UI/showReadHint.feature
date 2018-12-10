Feature: Käyttäjänä voin listata ainoastaan luettuja vinkkejä

  Scenario: käyttäjä luo vinkin, merkitsee luetuksi ja listaa luetun vinkin
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki id:llä 0, otsikolla "Kirja", kuvauksella "hieno teos", tagilla "kirja" ja urlilla "www.tekijanKommentit.com"
    When Käyttäjä valitsee vinkin  näyttämisen ja antaa id 0 ja antaa syötteen "y"
    And Käyttäjä valitsee luettujen vinkkien listauksen
    Then Ohjelma tulostaa "ID: 0"
    And Ohjelma tulostaa "Otsikko: Kirja"
    Then Ohjelma pysähtyy

Scenario: käyttäjä luo vinkin, jättää merkitsemättä luetuksi ja listaa luetun vinkin
    Given Ohjelma on käynnistetty
    And Tietokantaan on tallennettu vinkki id:llä 1, otsikolla "Kirja", kuvauksella "hieno teos", tagilla "kirja" ja urlilla "www.tekijanKommentit.com"
    When Käyttäjä valitsee vinkin  näyttämisen ja antaa id 1 ja antaa syötteen "n"
    And Käyttäjä valitsee luettujen vinkkien listauksen
    Then Ohjelma ei tulosta "ID: 1"
    And Ohjelma ei tulosta "Otsikko: Kirja"
    Then Ohjelma pysähtyy
