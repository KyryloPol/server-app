# BI-TJV-SemesterWork


## TrainMe pro fitness kluby
Aplikace je vhodná pro prémiové fitness kluby s dlouhou historií, a to je důvod, proč jsem se rozhodl vyvinout aplikaci pro snadný zápis do cvičení k profesionálním trenérům. Každý trenér má svůj vlastní nahraný kalendář, stručné informace o sobě. Vývoj této aplikace umožní zákazníkům klubu, aby si rychle a snadno vybrali osobního trenéra a vhodný rozvrh tělocvíků. 
## Klientská bussiness operace 
Klient si bude moci přidávat nové tréninky, měnít zápsané a trenér přidávat nové hodiny. Ke každému treningu si bude moci zapisovat místnost provedení cvíku. 

## Datový model serverové části 
<img src="diagram_1_.png" style="background-color: white"/> 
Takto vypadá datový model naší databáze.

## Komplexní dotaz 
Komplexní dotaz by samozřejmě mohlo vypadat takto: Kdo bude nejlepším trenérem měsíce podle ohodnocení provedených tréninků a kdo může požadovat zvýšení platu.

## Instalování klienta
Nejprve pro spuštění klienta musíte nainstalovat npm a Node.js a poté naklonovat repositář [https://gitlab.fit.cvut.cz/poliskyr/bi-tjv-semesterwork-client](https://gitlab.fit.cvut.cz/poliskyr/bi-tjv-semesterwork-client).  
Chcete-li nainstalovat npm (správce balíčků uzlů), budete muset mít Node.js nainstalován na vašem systému.  npm je součástí uzlu.js, takže pokud ji máte nainstalovanou,měli byste již mít nainstalovanou npm.

Chcete-li zkontrolovat, zda máte Node.js nainstalované js a npm, můžete otevřít okno terminálu a zadat následující příkazy:
```
node -v
npm -v
```
Tím se vytisknou čísla verzí Node.js a npm, resp. Pokud vidíte číslo verze, máte je nainstalované.

Pokud nemáte nainstalované Node.js a npm, můžete je nainstalovat podle následujících kroků:

- Stáhněte si nejnovější verzi Node.js z oficiálních webových stránek: https://nodejs.org/
- Postupujte podle pokynů k instalaci uzlu.js ve vašem systému. Tím se také nainstaluje npm.
- Případně, pokud používáte správce balíčků, jako je brew nebo apt-get na macOS nebo Linux, můžete nainstalovat Node.js a npm spuštěním následujícího příkazu:

```
brew install node
```
nebo
```
apt-get install nodejs
```

Poté jak jste naklonovali úložiště a nainstalovali npm:
- cd trainme-app-client
- npm i (v hlavním adresáři)
- npm run start (v hlavním adresáři)

## Instalování serveru
- Stáhněte si z artefaktů úspěšného běhu pipeline zkompilovaný projekt.
- Artefakt rozbalíme. Předpokládám, že jste ho uložili do 
- ```$HOME/Downloads. cd ~/Downloads; unzip Maven_artifacts_from*.zip```
- Ted' můžete aplikaci spustit ```java -jar treain-me-app-0.0.1-SNAPSHOT.jar```

## Instalování databáze
Nejprve instalujte MySQL Community Server from [https://dev.mysql.com/doc/mysql-apt-repo-quick-guide/en/#apt-repo-fresh-install](https://dev.mysql.com/doc/mysql-apt-repo-quick-guide/en/#apt-repo-fresh-install)
<br></br>
Nainstalujte MySQL Workbench from [https://dev.mysql.com/downloads/workbench/](https://dev.mysql.com/downloads/workbench/)
<br></br>
Vytvořte nové připojení on MySQL Workbench:
- Pojmenujte toto připojení, jak chcete, např: ```trainme-app```
- Nechte Hostname a port bez změny
- Username: ```root```
- Password: ```blank``` (nechte prazdné pole)


Poté jak máte vše nainstalováno importujte databáze z adresářu ```assets/``` do MySQL Workbench (```trainme.sql``` - zde existují testovácí data, ```trainme.sql``` - zde není žádná data, pouze struktura).
<br></br>
Tím padem mužete spustit aplikaci a bude automatické nastavené přípojení 