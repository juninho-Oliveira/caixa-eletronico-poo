package ProjetoJadir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe reponsável pela interface gráfica do caixa eletronico Utiliza Java
 * Swing para criar a janela e os botoes conforme o modelo da interface
 * Comunica-se com a logica do caixa atraves da interface ICaixaEletronico.
 */
public class GUI extends JFrame {
	// Referencia para o objeto que contem a logica do caixa eletronico
	private ICaixaEletronico caixa;

	/**
	 * Construtor da interface grafica Recebe o objeto do caixa e monta a janela
	 * 
	 * @param caixa objeto que implemente ICaixaEletronico
	 */
	public GUI(ICaixaEletronico caixa) {
		this.caixa = caixa;
		configurarJanela();
	}

	/**
	 * COnfigura e monta todos os componentes visuais da janela. Organiza os botoes
	 * nos tres modulos: Cliente, Administrador e Ambos
	 */
	private void configurarJanela() {
		setTitle("Caixa Eletronico");
		// Impede fechar a janela pelo X sem passar pelo extrato
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());

		// Painel principal com layout vertical (componentes empilhados)
		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
		painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		painelPrincipal.setBackground(Color.LIGHT_GRAY);

		// -----------Modulo do Cliente
		JLabel lblCliente = new JLabel("Modulo do Cliente:");
		lblCliente.setFont(new Font("Arial", Font.BOLD, 12));
		lblCliente.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Botao que permite ao cliente solicitar um saque
		JButton btnSaque = criarBotao("Efetuar Saque");
		btnSaque.addActionListener(e -> efetuarSaque());

		// ----------Modulo do Administrador
		JLabel lblAdmin = new JLabel("Modulo do Administrador:");
		lblAdmin.setFont(new Font("Arial", Font.BOLD, 12));
		lblAdmin.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Botao que exibe o relatorio de cedulas disponiveis
		JButton btnRelatorio = criarBotao("Relatorio de Cedulas");
		btnRelatorio.addActionListener(e -> mostrarRelatorio());

		// Botao que exibe o valor total em dinheiro no caixa
		JButton btnValorTotal = criarBotao("Valor total disponível");
		btnValorTotal.addActionListener(e -> mostrarValorTotal());

		// Botao que permite repor cedulas no caixa
		JButton btnReposicao = criarBotao("Reposicao de Cedulas");
		btnReposicao.addActionListener(e -> reporCedulas());

		// Botao que define o valor minimo do caixa (cota minima)
		JButton btnCota = criarBotao("Cota Minima");
		btnCota.addActionListener(e -> definirCotaMinima());

		// ----------Modulo de Ambos
		JLabel lblAmbos = new JLabel("Modulo de Ambos:");
		lblAmbos.setFont(new Font("Arial", Font.BOLD, 12));
		lblAmbos.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Botao que exibe o extrato e encerra a aplicacao
		JButton btnSair = criarBotao("Sair");
		btnSair.addActionListener(e -> sair());

		// Adiciona todos os componentes ao painel principal com espaçamentos
		painelPrincipal.add(lblCliente);
		painelPrincipal.add(Box.createRigidArea(new Dimension(0, 4)));
		painelPrincipal.add(btnSaque);
		painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

		painelPrincipal.add(new JSeparator()); // linha separadora
		painelPrincipal.add(Box.createRigidArea(new Dimension(0, 6)));

		painelPrincipal.add(lblAdmin);
		painelPrincipal.add(Box.createRigidArea(new Dimension(0, 4)));
		painelPrincipal.add(btnRelatorio);
		painelPrincipal.add(Box.createRigidArea(new Dimension(0, 4)));
		painelPrincipal.add(btnValorTotal);
		painelPrincipal.add(Box.createRigidArea(new Dimension(0, 4)));
		painelPrincipal.add(btnReposicao);
		painelPrincipal.add(Box.createRigidArea(new Dimension(0, 4)));
		painelPrincipal.add(btnCota);
		painelPrincipal.add(Box.createRigidArea(new Dimension(0, 4)));

		painelPrincipal.add(new JSeparator()); // Linha Separadora
		painelPrincipal.add(Box.createRigidArea(new Dimension(0, 6)));

		painelPrincipal.add(lblAmbos);
		painelPrincipal.add(Box.createRigidArea(new Dimension(0, 4)));
		painelPrincipal.add(btnSair);

		add(painelPrincipal, BorderLayout.CENTER);

		// Ajusta o tamanho da janela automaticamente e centraliza na tela
		pack();
		setMinimumSize(new Dimension(280, 300));
		setLocationRelativeTo(null);
	}

	/**
	 * Metodo auxiliar que cria um botao padronizado Todos os botoes ocupam a
	 * largura total do painel
	 * 
	 * @param texto texto exibido no botao
	 * @return botao configurado
	 */
	private JButton criarBotao(String texto) {
		JButton btn = new JButton(texto);
		btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		btn.setAlignmentX(Component.LEFT_ALIGNMENT);
		return btn;
	}

	/**
	 * Acao do botao "Efetuar Saque".
	 * Solicita o valor ao cliente e chama o metodo sacar() do caixa.
	 * Exibe o resultado em uma janela de dialogo.
	 */
	private void efetuarSaque() {
		//pede o valor do saque ao usuario
		String entrada = JOptionPane.showInputDialog(this, "Informe o valor do saque (R$):", "Efetuar Saque", JOptionPane.PLAIN_MESSAGE);
		if (entrada == null) return; //usuario cancelou
		
		try {
			int valor = Integer.parseInt(entrada.trim());
			String resultado = caixa.sacar(valor); // chama a logica do saque
			JOptionPane.showMessageDialog(this, resultado, "Resultado do Saque", JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException ex) {
				//Caso o usuario digite algo que nao seja numero
				JOptionPane.showMessageDialog(this, "Por favor, informe um valor numerico valido.", "Erro", JOptionPane.ERROR_MESSAGE);
			} 
	}
	

	/**
	 * Acao do botao "Relatorio de Cedulas".
	 * Exibe o relatorio de cedulas em uma janela com area de texto rolavel
	 */
	private void mostrarRelatorio() {
		String relatorio = caixa.pegaRelatorioCedulas();
		JTextArea area = new JTextArea(relatorio);
		area.setEditable(false);
		area.setFont(new Font("Monospaced", Font.PLAIN, 12)); //fonte monoespaca para alinhar colunas
		JOptionPane.showMessageDialog(this, new JScrollPane(area), "Relatorio de Cedulas", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Acao do botao "Valor total disponível"
	 * Exibe o valor total em reais que o caixa possui.
	 */
	private void mostrarValorTotal() {
		String valor = caixa.pegaValorTotalDisponivel();
		JOptionPane.showMessageDialog(this, valor, "Valor Total Disponivel", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Acao do botao "Reposicao de Cedulas"
	 * Permite ao administrador escolher o tipo de cedular e a quantidade a repor
	 * Usa um menu de selecao para escolher o valor da cedula
	 */
	private void reporCedulas() {
		//Opcoes de cedulas disponiveis no caixa
		String[] opcoes = {"100", "50", "20", "10", "5", "2"};
		String cedula = (String) JOptionPane.showInputDialog(
			this,
			"Selecione a cedula para reposicao:",
			"Reposiucao de Cedulas",
			JOptionPane.PLAIN_MESSAGE,
			null,
			opcoes,
			opcoes[0]
		);
		if (cedula == null) return; //usuario cancelou
		
		//Pede a quantidade a ser reposta
		String qtdStr = JOptionPane.showInputDialog(this,
				"Quantidade de cedulas de R$ " + cedula + " a adicionar:",
				"Reposicao de Cedulas",
				JOptionPane.PLAIN_MESSAGE);
		if(qtdStr == null) return; //usuario cancelou
		
		try {
			int quantidade = Integer.parseInt(qtdStr.trim());
			String resultado = caixa.reposicaoCedulas(Integer.parseInt(cedula), quantidade);
			JOptionPane.showMessageDialog(this, resultado, "Reposicao", JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Quantidade Invalida.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Acao do botao "Cota Minima".
	 * Permite ao administrador definir o valor minimo do caixa
	 * Quando o saldo cair abaixo desse valor, o caixa para de atender os clientes
	 */
	private void definirCotaMinima() {
		String entrada = JOptionPane.showInputDialog(this,
				"Informe o valor da cota minima (R$):",
				"Cota Minima",
				JOptionPane.PLAIN_MESSAGE);
		if (entrada == null) return; //usuario cancelou
		
		try {int minimo = Integer.parseInt(entrada.trim());
		String resultado = caixa.armazenaCotaMinima(minimo);
		JOptionPane.showMessageDialog(this, resultado, "Cota Minima", JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Valor invalido.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Acao do botao "Sair"
	 * Exibe o extrato completo de todos os saques realizados na sessao
	 * e encerra a aplicacao
	 */
	private void sair() {
		//Obtem o extrado do CaixaEletronico e exibe em uma janela rolavel
		String extrato = ((CaixaEletronico) caixa).getExtrato();
		JTextArea area = new JTextArea(extrato);
		area.setEditable(false);
		area.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JOptionPane.showMessageDialog(this, new JScrollPane(area), "Extrato", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0); //encerra a aplicacao
	}
	
	/**
	 * Torna a janela visivel
	 * Chamado pelo metodo main() do CaixaEletronico.
	 */
	public void mostrar() {
		setVisible(true);
	}
}
