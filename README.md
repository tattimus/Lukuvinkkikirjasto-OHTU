# Lukuvinkkikirjasto-OHTU
[![Build Status](https://travis-ci.org/tattimus/Lukuvinkkikirjasto-OHTU.svg?branch=master)](https://travis-ci.org/tattimus/Lukuvinkkikirjasto-OHTU)
[![codecov](https://codecov.io/gh/tattimus/Lukuvinkkikirjasto-OHTU/branch/master/graph/badge.svg)](https://codecov.io/gh/tattimus/Lukuvinkkikirjasto-OHTU)
[![MIT license](https://img.shields.io/github/license/tattimus/Lukuvinkkikirjasto-OHTU.svg)](LICENSE)


Ryhmän nimi: *Ohjelmistontuottajat*

* [Burndown-käyrä](https://docs.google.com/spreadsheets/d/1F60Hm_2pOSuHzeR_-toV5Pt2lhebRR9-kvPmpjvZurc/edit?usp=sharing)

**Defenition of done:**
* Ohjelmoitu
* Testattu
* Täyttää hyväksymäkriteerit
* Integroitu osaksi tuotetta. 

## Backlogit

* [Project 
Backlog](https://github.com/tattimus/Lukuvinkkikirjasto-OHTU/projects/2)
* [Sprint 
backlog](https://github.com/tattimus/Lukuvinkkikirjasto-OHTU/projects/1)

## Käyttöohje

Ohjelmatiedoston Lukuvinkkikirjasto-all.jar saat ladattua [täältä](https://github.com/tattimus/Lukuvinkkikirjasto-OHTU/releases).
Avaa komentorivi ja navigoi ohjelmatiedoston sisältävään kansioon. Käynnistä ohjelma komennolla:
```
java -jar Lukuvinkkikirjasto-all.jar
```

Ohjelman päävalikosta voit valita haluamasi toiminnon toiminnon edessä sulkeissa olevalla numerolla. Valittavissa olevat toiminnot ovat:
* **Lisää vinkki** Syötä vinkin tiedot pyydettäessä.
* **Hae tagilla** Tagin syöttämällä näet siihen liitetyt vinkit.
* **Poista vinkki** Syötä poistettavan vinkin id.
* **Listaa vinkit** Tulostuu lista kaikista ohjelmaan tallennetuista vinkeistä.
* **Näytä vinkki** Syötä näytettävän vinkin id. Näyttämisen yhteydessä voit merkitä vinkin luetuksi. Luetuksi merkitsemisen jälkeen vinkin tiedoissa näkyy tieto siitä, milloin se on luettu.
* **Muokkaa vinkkiä** Syötä ne tiedot jotka haluat muuttaa. Voit myös lisätä ja poistaa tageja sekä tekijätietoja.
* **Lisää ISBN:n perusteella** Syötä kirjan ISBN. Ohjelma kysyy puuttuvat tiedot ja tallettaa vinkin  kantaan.
* **Listaa luetut vinkit** Ohjelma listaa kaikki vinkit jotka on merkitty luetuiksi.
* **Lopeta** Ohjelman suoritus lopetetaan.
