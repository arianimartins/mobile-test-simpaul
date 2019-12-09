# Teste para Engenheiro de Software Mobile

A proposta deste projeto é criar um App para listar os personagens da marvel e ao selecioná-los conseguir vizualizar uma tela com a descrição do personagem. Você poderá implementar seu App em IOS(swift)/Android(Kotlin)/Flutter(Dart), dependendo da tecnología selecionada você deverá aplicar uma das seguintes Arquiteturas MVVM/MVP/BLoC. Para implementar o seu App você deverá criar uma conta e gerar uma chave de acesso na API. Seguem os detalhes da API.

## Detalhes API
  - [Site da API](https://developer.marvel.com/documentation/getting_started)
  - Uma vez gerada sua chave você usará o seguinte endPoint para realizar os request e implementar seu App [charactersEndPoint](http://gateway.marvel.com/v1/public/characters) usando suas credenciais.
  
## Especificação do App
  - Criar uma SplashScreen, ela deverá ter o logotipo da aplicação.
  - O App vai ter um menu iniciar com dois items. Um item do menu irá navegar para uma tela de lista e o outro pra uma tela "Sobre/perfil" com o logo do aplicativo, nome do desenvolvedor e versão do aplicativo.
  - A tela de lista vai listar os personagens retornados da Api, deverá ser mostrada a imagem e o nome do personagem.
  - Ao selecionar um personagem na lista o App vai navegar para uma tela de detalhes mostrando os dados do personagem incluindo a descrição retornada da Api.
  - Na tela de detalhe deve existir um botão que permita favoritar um personagem, ao favoritar um personagem ele ficará em outra cor na lista. Se o personagem já estiver como favorito, ao fazer o touch no botão ele não será mais favorito e a alteração deve ser refletida na lista.
  
## Detalhes
  - Implemente seu App seguindo as melhores práticas de arquitetura e design patterns.
  - Utilize as libs que ache necessário na implementação do seu app.
  - Detalhe no README.md as informações necessárias para que podamos avaliar seu teste.
  - Para realizar o teste faça um Fork do repositório e trabalhe no branch com seu nome-sobrenome. Quando terminar faça um PullRequest deste branch. 

## Prazo para entrega
  Você terá 3 dias a partir do recebimento do email para entregar o seu App.
