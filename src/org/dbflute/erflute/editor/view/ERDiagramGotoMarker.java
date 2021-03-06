package org.dbflute.erflute.editor.view;

import org.dbflute.erflute.editor.MainDiagramEditor;
import org.eclipse.core.resources.IMarker;
import org.eclipse.gef.EditPart;
import org.eclipse.ui.ide.IGotoMarker;

public class ERDiagramGotoMarker implements IGotoMarker {

    private MainDiagramEditor editor;

    public ERDiagramGotoMarker(MainDiagramEditor editor) {
        this.editor = editor;
    }

    public void gotoMarker(IMarker marker) {
        focus(this.editor.getMarkedObject(marker));
    }

    private void focus(Object object) {
        EditPart editPart = (EditPart) this.editor.getGraphicalViewer().getEditPartRegistry().get(object);

        if (editPart != null) {
            this.editor.getGraphicalViewer().select(editPart);
            this.editor.getGraphicalViewer().reveal(editPart);
        }
    }
}
