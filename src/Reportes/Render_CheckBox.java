/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author bzc85r
 */
public class Render_CheckBox extends JCheckBox implements TableCellRenderer {
      private JComponent component = new JCheckBox();

    /** Constructor de clase */
    public Render_CheckBox() {
        setOpaque(true);
    }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      //Color de fondo de la celda
      boolean b = ((Boolean) value).booleanValue();
      if(b)
      ( (JCheckBox) component).setBackground( new Color(0,200,0) );
      else
      ( (JCheckBox) component).setBackground( Color.RED );
      //obtiene valor boolean y coloca valor en el JCheckBox
      
      ( (JCheckBox) component).setSelected( b );
      return ( (JCheckBox) component);
  }


}
