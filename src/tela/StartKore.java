package tela;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StartKore {

	private List<File> pastas = new ArrayList<File>();
	private JFrame frmStartKore;
	private JTable table;

	public void OpenStartKore() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartKore window = new StartKore();
					window.frmStartKore.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StartKore() {
		initialize();
	}

	private void initialize() {
		if (new File("ordemkore").exists() == false)
			JOptionPane.showMessageDialog(null, "A pasta \"ordemkore\" não existe");
		
		File[] files = new File("ordemkore").listFiles();
		for (File file : files) {
			if (file.isDirectory())
				pastas.add(file);
		}
		
		if(pastas.size() == 0)
			JOptionPane.showMessageDialog(null, "A pasta \"ordemkore\" está vazia");
		
		frmStartKore = new JFrame();
		frmStartKore.setIconImage(Toolkit.getDefaultToolkit().getImage(StartKore.class.getResource("/img/yoshi.png")));
		frmStartKore.setTitle("StartKore");
		frmStartKore.setBounds(100, 100, 260, 1000);
		frmStartKore.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmStartKore.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		String[] columnNames = {"bot"};
		Object[][] contas = new Object[pastas.size()][1];
		for (int i = 0; i < pastas.size(); i++) {
			contas[i][0] = pastas.get(i).getName();
		}
		
		table = new JTable(contas, columnNames) {
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		frmStartKore.getContentPane().add(scrollPane);
		
		JButton btn = new JButton("Start");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Aperte \"ok\" para abrir o(s) bot(s)");
				for (int row : table.getSelectedRows()) {
					Iniciar(table.getValueAt(row, 0).toString());
				}
			}
		});
		scrollPane.setRowHeaderView(btn);
		
		frmStartKore.setExtendedState(JFrame.MAXIMIZED_VERT);
		frmStartKore.setLocationRelativeTo(null);
	}

	public void Iniciar(String nome) {
		try {
			Runtime.getRuntime().exec(new String[] { "cmd", "/C", "start " + nome }, null, new File("ordemkore\\" + nome));
			Esperar();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir o \"" + nome + "\"");
		}
	}
	
	public void Esperar() {
		try {
			new Thread();
			Thread.sleep(100);
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Erro");
		}
	}
}
