[![](https://jitpack.io/v/ValeraShimchuck/JSP.svg)](https://jitpack.io/#ValeraShimchuck/JSP)
# Документация JSP 

## Документация spworlds-api
https://github.com/sp-worlds/api-docs
________________________
## Преимущества над остальными
- Асинхронность - api выполняет все запросы в асинке, 
что позволяет делать несколько запросов одновременно, без потери
производительности.
- Расширяемость - в случае добавления новых фич,
код будет оставаться таким же читабельным.
- Защищенность - если вы опечатались или случайно перепутали 
разные данные, то вместо того, чтобы кидать 400, api вам укажет
на вашу ошибку.
___________________
## Как начать
Для начала скачайте или забилдите последний .jar файл, а потом импортируйте в проект.
Также доступен репозиторий на JitPack. Версия Java - 8
### Как импортировать через gradle/maven
#### maven
    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

    <dependency>
	    <groupId>com.github.ValeraShimchuck</groupId>
	    <artifactId>JSP</artifactId>
	    <version>1.4</version>
	</dependency>
#### gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
	        implementation 'com.github.ValeraShimchuck:JSP:1.4'
	}
### Как отправить деньги на карту
    String walletId = "7bfaa2cc-628a-44e9-a6a0-d0d25e6ecae1";
    String walletToken = "wvb7Rc9tf91ipiN2AzHipS3/RAvhLc0H";
    String walletNumber = "49931";
    String comment = "test send";
    int amount = 1;
    IWallet wallet = new Wallet(walletId, walletToken);
    wallet.sendMoney(walletNumber, amount, comment);
### Как получить пользователя
    String walletId = "7bfaa2cc-628a-44e9-a6a0-d0d25e6ecae1";
    String walletToken = "wvb7Rc9tf91ipiN2AzHipS3/RAvhLc0H";
    WalletKey key = new WalletKey(walletId, walletToken);
    String discordID = "317340731381645316";
    IUser user = User.getUser(key,discordID).join().orElse(null);
    user.getName(); // получить ник игрока
### Как отправить запрос на покупку
    String walletId = "7bfaa2cc-628a-44e9-a6a0-d0d25e6ecae1";
    String walletToken = "wvb7Rc9tf91ipiN2AzHipS3/RAvhLc0H";
    WalletKey key = new WalletKey(walletId, walletToken);
    String data = "some data";
    URI redirect = URI.create("https://redirect.test");
    URI webhook = URI.create("https://webhook.test");
    int amount = 1;
    IPayment payment = new Payment(amount, redirect, webhook, data, key);
    URI toSendUserURI = payment.sendPayment().join();
Где toSendUserURI это ссылка на которую нужно отправить пользователя.
При получении информации об успешной оплате вы получите JSON в котором:
* payer - Ник игрока, который совершил оплату
* amount - Стоимость покупки
* data - данные которые отдали при создании запроса на оплату.

Для того чтобы проверить оплату вы должны проверить хедер X-Body-Hash ответа, через:
    
    String xBodyHash = "some hash"; // get hash from header
    boolean isValid = payment.getValidator(xBodyHash);

____
## Типы данных
- DiscordID - является уникальным идентификатором пользователя состоящий из 18 цифр
- WalletKey - является ключем карты, который состоит из WalletId:WalletToken
- WalletId - представляет собой многоразовый UUID, который можно получить на сайте spworlds
- WalletToken - представляет собой токен состоящий из букв, цифр, \\ и + размером 32 символа, сгенерировать можно на сайте spworlds
- WalletNumber - является номером карты состоящий из 5 цифр.
- User - пользователь.
