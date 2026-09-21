/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author ArthurAquino
 */
public class Jogo extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Jogo.class.getName());
    
    // E O LOCAL ONDE CRIAMOS AS NOSSAS VARIAVEIS
    
    //JBbutton precisa da importacao da sua biblioteca
    //btnCampos e o nome da variavel - (voce que escolhe)
    //matriz com - 10 linhas e 10 colunas
    JButton [][] btnCampos = new JButton[10][10];
    
    //MATRIZ PARA GUARDAR BOMBAS - true p/bomba, false p/ numero
    boolean [][] bombas = new boolean [10][10];
    
    //MATRIZ PARA GUARDAR OS CAMPOS QUE FORAM ABERTOS
    
    boolean [][] abertos = new boolean[10][10];
    
    int quantidadeBombas = 15;
    int quantidadeCasasAbertas=0;
    
    boolean jogoencerrado= false;
    
    int segundosPassados = 0;
    Timer cronometro;

    /**
     * Creates new form Jogo
     */
    //Construtor da classe sem ele a tela nao funciona
    public Jogo() {
        initComponents();
        //definir tamanho para o painel
        painelCampo.setPreferredSize(new Dimension(900,700));
        
        CriarTabuleiro();
        
        
    }
    
    
    //CRIAR AS NOSSAS FUNCOES/METODOS
    
    public void CriarTabuleiro(){
        
        painelCampo.setLayout(new GridLayout(10,10,2,2));
        
        for(int coluna=0;coluna<=9;coluna++){
           for(int linha=0;linha<=9;linha++){
               //variavel botao para guardar os dados provisorios
              JButton botao = new JButton();
                botao.setFont(new Font("Arial",Font.BOLD,16));// fonte
                botao.setBackground(new Color(255,192,230));// cor de fundo
                botao.setForeground(Color.WHITE);// cor de texto
                
                // remover marcas do botão que vem por padrão
                botao.setFocusPainted(false);
                botao.setEnabled(false);
                
                final int linhaSelecionada= linha;
                final int colunaSelecionada = coluna;
                
                //adicionar o evento de clique para abrir as casas
                botao.addActionListener((ActionEvent Evento)->{
                       abrirBotao(linhaSelecionada,colunaSelecionada); 
                
                            });
                
                
                
                //adicionar o botao dentro da matriz
                btnCampos[linha][coluna]=botao;
                // adicionar ele dentro do painel
                painelCampo.add(botao);
               
               
           }//fim do 2º for
    }//fim do 1° for
    
    }// fim do metodo criar tabuleiro
    
    public void AdicionarBombas(){
       //Criar uma variavel random para gerar valores aleatorios
       Random sorteador = new Random();
       int bombasAdicionadas = 0;
       
       while(bombasAdicionadas<quantidadeBombas){
           // sortear o n° da linha e coluna que vai ficar a bomba
           int linha = sorteador.nextInt(10);
           int coluna = sorteador.nextInt(10);
           //vericar se nao existe bomba adicionada no local
           if(!bombas[linha][coluna]){
               //adicionar a bomba na matriz
               bombas[linha][coluna]=true;
               bombasAdicionadas++;
           }
           
       }
    
    
    }// fim do addBombas
    
    public void Iniciarjogo(){
        LimparJogo();
        //chamar o metodo adicionarBombas
        AdicionarBombas();
        IniciarCronometro();
        //depois precisamos iniciar os botoes do jogo
        for(int colunas=0;colunas<=9;colunas++){
           for(int linhas=0; linhas<=9;linhas++){
             JButton botao = btnCampos[linhas][colunas];
             botao.setEnabled(true);
           }//fim do 2° for 
        }//fim do 1°for
        btniniciar.setText("REINICIAR");
    }//fim do iniciarjogo
    
    public void abrirBotao(int linha, int coluna){
        //verificar se o jogo foi finalizado
        if(jogoencerrado){
            return;
        }
        //verificar  se o botao ja foi aberto
        if (abertos[linha][coluna]){
            return;
        }
        /*se o jogo ainda estiver rodando e o botao ainda nao tiver
        sido aberto - entao vamos abrir o botao
        */
        abertos[linha][coluna]=true;
        quantidadeCasasAbertas++;
        
        //acessar o que tem dentro do botao
        JButton botao = btnCampos[linha][coluna];
        //se no botao tiver ma bomba, entao vamos mostrr a bomba a ele
        if(bombas[linha][coluna]){
            ImageIcon imgBomba = new ImageIcon(
                    getClass().getResource("/assets/bomb.png"));
            //colocar a imagem no botao
            botao.setIcon(imgBomba);
            FinalizarJogo(false);
            return;
        }else{ImageIcon imgBandeira = new ImageIcon(
                    getClass().getResource("/assets/flag.png"));
            botao.setIcon(imgBandeira);
            return;
        }
 
        
    }// fim do metodo abrirBotao
    
    
    //este metodo informa quando a pessoa perder ou ganhar o jogo
    public void FinalizarJogo(boolean venceu){
        mostrarBombas();
        //informar que o jogo acabou
        jogoencerrado=true;
        cronometro.stop();
        
        //verificar se a pessoa venceu ou nao
        if(venceu){
            JOptionPane.showMessageDialog(
                    this,"Parabens voce venceu o jogo!");
        }else{
            JOptionPane.showMessageDialog(
            this,"Dannn!");
           LimparJogo();
        }
        
        }//fim do FinalizarJogo
    
    
    public void VerificarVitoria(){
        // armazenar a quantidade de casas com bandeiras
        int casasSemBomba= 100 - quantidadeBombas;
        //se a pessoa abriu as bandeiras e nao abriu nenhuma bomba entao ela venceu o jogo, e o finalozarJogo imprime a mensagem 
        if(quantidadeCasasAbertas == casasSemBomba){
            FinalizarJogo(true);
        }
                
    }
    
    
    public void IniciarCronometro(){
        //zerar o cronometro caso tenha tido um jogo anterior
        if (cronometro !=null){
            cronometro.stop();
        }
        //reseta o cronometro
        segundosPassados = 0;
        tfTempo.setText("00:00");
        
        //converter o tempo em minutos e segundos
        // o cronometo conta de 1 em 1 segundo, e vai convertendo
        cronometro = new Timer(1000, Evento->{
        segundosPassados++;
        int minutos = segundosPassados/60;
        int horas = minutos/60;
        int segundos = segundosPassados%60;
        //mostrar o tempo dentro da variavel
        tfTempo.setText(
        String.format("%02d:%02d:%02d",horas,minutos,segundos));
        });
        
        cronometro.start();
        
        
        
    }
    
    
    public void LimparJogo(){
        quantidadeCasasAbertas=0;
        jogoencerrado=false;
        
        for(int coluna=0;coluna<=9;coluna++){
            for(int linha=0;linha<=9;linha++){
                bombas[linha][coluna]=false;
                abertos[linha][coluna]=false;
                
                
                //limpeza dos botoes
                JButton botao = btnCampos[linha][coluna];
                botao.setIcon(null); 
                
            }//fim do 2 for
        }//fim do 1 for
     AdicionarBombas();   
    IniciarCronometro();
    
    }//fim do limpar jogo
    
    
    public void mostrarBombas(){
      for(int coluna=0;coluna<=9;coluna++){
          for(int linha=0;linha<=9;linha++){
            JButton botao = btnCampos[linha][coluna];
        //se no botao tiver ma bomba, entao vamos mostrr a bomba a ele
        if(bombas[linha][coluna]){
            ImageIcon imgBomba = new ImageIcon(
                    getClass().getResource("/assets/bomb.png"));
            //colocar a imagem no botao
            botao.setIcon(imgBomba);
            
            
        }//fim do if  
          }//fim do segundo for
      }//fim do 1 for
        
        
    
    }//fim do mostrar bombas
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        titulo = new javax.swing.JLabel();
        btniniciar = new javax.swing.JButton();
        tfTempo = new javax.swing.JTextField();
        painelCampo = new javax.swing.JPanel();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titulo.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        titulo.setForeground(new java.awt.Color(0, 204, 102));
        titulo.setText("Campo Minado");

        btniniciar.setBackground(new java.awt.Color(0, 153, 0));
        btniniciar.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btniniciar.setText("Iniciar");
        btniniciar.addActionListener(this::btniniciarActionPerformed);

        tfTempo.setEditable(false);
        tfTempo.setBackground(new java.awt.Color(0, 153, 0));
        tfTempo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        tfTempo.setForeground(new java.awt.Color(255, 255, 255));
        tfTempo.setText("00:00");

        painelCampo.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout painelCampoLayout = new javax.swing.GroupLayout(painelCampo);
        painelCampo.setLayout(painelCampoLayout);
        painelCampoLayout.setHorizontalGroup(
            painelCampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        painelCampoLayout.setVerticalGroup(
            painelCampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCampo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(tfTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(btniniciar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titulo)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(tfTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(btniniciar)
                .addGap(26, 26, 26)
                .addComponent(painelCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btniniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btniniciarActionPerformed
        // TODO add your handling code here:
       Iniciarjogo(); 
    }//GEN-LAST:event_btniniciarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Jogo().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btniniciar;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JPanel painelCampo;
    private javax.swing.JTextField tfTempo;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
