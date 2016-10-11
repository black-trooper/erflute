package org.dbflute.erflute.editor.controller.editpart.element.node;

import java.beans.PropertyChangeEvent;

import org.dbflute.erflute.editor.controller.command.category.ChangeVGroupNameCommand;
import org.dbflute.erflute.editor.controller.editpolicy.element.node.DiagramWalkerComponentEditPolicy;
import org.dbflute.erflute.editor.model.ERDiagram;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.Location;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.DiagramWalker;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.ermodel.VGroup;
import org.dbflute.erflute.editor.view.figure.VGroupFigure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.PlatformUI;

public class VGroupEditPart extends DiagramWalkerEditPart implements IResizable {

    public VGroupEditPart() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IFigure createFigure() {
        VGroup vgroup = (VGroup) this.getModel();
        VGroupFigure figure = new VGroupFigure(vgroup.getName());

        return figure;
    }

    @Override
    public void doPropertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(VGroup.PROPERTY_CHANGE_VGROUP)) {
            refreshChildren();
            //			refresh();
            refreshVisuals();
        }

        super.doPropertyChange(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Rectangle getRectangle() {
        Rectangle rectangle = super.getRectangle();

        VGroup group = (VGroup) this.getModel();
        ERModelEditPart rootEditPart = (ERModelEditPart) getRoot().getContents();
        //		ERDiagramEditPart rootEditPart = (ERDiagramEditPart) this.getRoot().getContents();

        for (Object child : rootEditPart.getChildren()) {
            if (child instanceof DiagramWalkerEditPart) {
                DiagramWalkerEditPart editPart = (DiagramWalkerEditPart) child;

                if (group.contains((DiagramWalker) editPart.getModel())) {
                    Rectangle bounds = editPart.getFigure().getBounds();

                    if (bounds.x + bounds.width > rectangle.x + rectangle.width) {
                        rectangle.width = bounds.x + bounds.width - rectangle.x;
                    }
                    if (bounds.y + bounds.height > rectangle.y + rectangle.height) {
                        rectangle.height = bounds.y + bounds.height - rectangle.y;
                    }

                    if (rectangle.width != group.getWidth() || rectangle.height != group.getHeight()) {
                        group.setLocation(new Location(group.getX(), group.getY(), rectangle.width, rectangle.height));
                    }
                }
            }
        }

        return rectangle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createEditPolicies() {
        this.installEditPolicy(EditPolicy.COMPONENT_ROLE, new DiagramWalkerComponentEditPolicy());

        super.createEditPolicies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void performRequestOpen() {
        VGroup group = (VGroup) this.getModel();
        ERDiagram diagram = this.getDiagram();

        //		VGroup copyGroup = group.clone();

        InputDialog dialog =
                new InputDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "�O���[�v���ύX",
                        "�O���[�v�����͂��ĉ������B", group.getName(), null);

        if (dialog.open() == IDialogConstants.OK_ID) {
            CompoundCommand command = new CompoundCommand();
            command.add(new ChangeVGroupNameCommand(diagram, group, dialog.getValue()));
            this.executeCommand(command.unwrap());
        }
    }

}
