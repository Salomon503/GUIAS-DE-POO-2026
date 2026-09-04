
package com.mycompany.guia8;

import com.mycompany.guia8.interfaces.FrmMenuPrincipal;
import javax.swing.SwingUtilities;

/**
 *
 * @author jhl32
 */
public class GUIA8 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmMenuPrincipal menu = new FrmMenuPrincipal();
            menu.setVisible(true);
        });
    }
}

