# Renomear
Simples aplicação java para renomear diversos arquivos usando um padrão definido pelo usuario.

# Versão Alfa 3
Agora com suporte a linha de comando, o Renomear agora pode ser executado com os mesmo recursos que o modo grafico possui (não totalmente).

# Como usar (Modo Grafico)

1- Dê dois cliques sobre o arquivo ou execute o seguinte comando, java -jar "nome do arquivo".jar
2- Irá surgir uma tela com o seguinte conteudo:
<\br>
``
- 4 caixas de opçoes contendo (inicialmente) Desativado.
- 4 entrada de texto vazias
- 1 botão com o nome previa
- 4 previas (com uma cor meio chatinho ): )
  Vou explicar pra que serve cada uma dessas coisas durante o guia de uso.
  ``
  <\br>
3- Nas caixas de opções selecione oque o tipo de regra que o termo (que voce irá escrever embaixo dele) irá ter:
<\br>
``
- Numeral:
  É um numero que irá sofrer incremento de uma unidade a cada arquivo, atualmente pode escrever um numero com o zero no inicio que o mesmo não irá sumir.
- Constante:
  Pode ser qualquer coisa que pode ser escrita, podendo ser letras, numeros, caracteres... porém no caso dos numeros, eles não irão sofrer incremento.
``
<\br>
4- Embaixo de cada caixa escreva oque lhe convem... Esqueci, no espaço dos numeros só escreva numeros, sem espaço ou qualquer outra coisa.
<\br>
5- Veja 4 previa usando essa regra, caso o resultado seja o desejado prossiga, caso não seja, volte a etapa das regras. Caso encontre uma limitação entre em contado comigo.
<\br>
6- Surgiu um novo botão, Selecionar arquivos, ao clicar nesse botão será aberto uma janela onde voce pode selecionar os arquivos que deseja renomear. Selecionado os arquivos, confirme os mesmo e pronto, arquivos renomeado seguindo a regra que voce criou.
<\br>
# Como usar (Modo Texto)

1- Abra o terminal ou o promt de comando.
<\br>
2- execute o arquivo usando o seguinte comando em negrito , **java -jar "nome do arquivo".jar tm** , o parametro tm é o text mode, indica que voce quer usar no modo de texto.
<\br>
3- Siga oque se pede, dizendo o local dos arquivos. No modo de texto, ainda, possui algumas limitaçoes com relação a essa parte.
<\br>
4- Confirme o local, escolhendo somente o numero.
<\br>
5- Infomre a regra usando o mesmo principio do modo grafico:
<\br>
``
- Numeral:
  É um numero que irá sofrer incremento de uma unidade a cada arquivo, atualmente pode escrever um numero com o zero no inicio que o mesmo não irá sumir.
- Constante:
  Pode ser qualquer coisa que pode ser escrita, podendo ser letras, numeros, caracteres... porém no caso dos numeros, eles não irão sofrer incremento.
``
<\br>
6- Finalizado o procedimento, selecione a opçao para aplicar a regra.
<\br>
7- A mensagem a seguir contem o seguinte conteudo (podendo variar com a versão):
<\br>
``
Deseja ver uma previa?
1->Sim, com tres (3) arquivos
2->Sim, com todos os arquivos
3->Não, voltar pra regra
4->Não, executar regra
5->Não, executar regra com log

- No modo de texto é possivel ver duas previas, uma com tres arquivos e outra com todos os arquivos.
- É possivel executar a regra vendo o que foi feito, com a opção de log. Util para alguns casos.
``
<\br>
8- Finalmente é finalizada a aplicação informando o tempo que demorou pra fazer tudo isso.
