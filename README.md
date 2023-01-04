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

## Instalování
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

