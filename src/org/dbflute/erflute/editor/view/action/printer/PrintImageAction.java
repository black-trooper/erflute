package org.dbflute.erflute.editor.view.action.printer;

import org.dbflute.erflute.editor.MainDiagramEditor;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.print.PrintGraphicalViewerOperation;
import org.eclipse.gef.ui.actions.PrintAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.ui.IWorkbenchPart;

public class PrintImageAction extends PrintAction {

    public PrintImageAction(MainDiagramEditor part) {
        super((IWorkbenchPart) part);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        GraphicalViewer viewer;
        viewer = (GraphicalViewer) getWorkbenchPart().getAdapter(GraphicalViewer.class);

        PrintDialog dialog = new PrintDialog(viewer.getControl().getShell(), SWT.NULL);
        PrinterData data = dialog.open();

        if (data != null) {
            Printer printer = new Printer(data);
            PrintGraphicalViewerOperation op = new PrintERDiagramOperation(printer, viewer);

            op.run(getWorkbenchPart().getTitle());
        }
    }

}
