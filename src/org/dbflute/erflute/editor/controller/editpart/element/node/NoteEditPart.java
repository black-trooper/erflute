package org.dbflute.erflute.editor.controller.editpart.element.node;

import java.beans.PropertyChangeEvent;

import org.dbflute.erflute.editor.controller.editpolicy.element.node.DiagramWalkerComponentEditPolicy;
import org.dbflute.erflute.editor.controller.editpolicy.element.node.note.NoteDirectEditPolicy;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.note.Note;
import org.dbflute.erflute.editor.view.editmanager.NoteCellEditor;
import org.dbflute.erflute.editor.view.editmanager.NoteEditManager;
import org.dbflute.erflute.editor.view.editmanager.NoteEditorLocator;
import org.dbflute.erflute.editor.view.figure.NoteFigure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

public class NoteEditPart extends DiagramWalkerEditPart implements IResizable {

    private NoteEditManager editManager = null;

    /**
     * {@inheritDoc}
     */
    @Override
    protected IFigure createFigure() {
        NoteFigure noteFigure = new NoteFigure();

        this.changeFont(noteFigure);

        return noteFigure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPropertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(Note.PROPERTY_CHANGE_NOTE)) {
            refreshVisuals();
        }

        super.doPropertyChange(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createEditPolicies() {
        this.installEditPolicy(EditPolicy.COMPONENT_ROLE, new DiagramWalkerComponentEditPolicy());
        this.installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new NoteDirectEditPolicy());

        super.createEditPolicies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refreshVisuals() {
        Note note = (Note) this.getModel();

        NoteFigure figure = (NoteFigure) this.getFigure();

        figure.setText(note.getText(), note.getColor());

        super.refreshVisuals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void performRequest(Request request) {
        if (request.getType().equals(RequestConstants.REQ_DIRECT_EDIT) || request.getType().equals(RequestConstants.REQ_OPEN)) {
            performDirectEdit();
        }
    }

    private void performDirectEdit() {
        if (this.editManager == null) {
            this.editManager = new NoteEditManager(this, NoteCellEditor.class, new NoteEditorLocator(getFigure()));
        }

        this.editManager.show();
    }

    @Override
    protected void performRequestOpen() {
    }
}
