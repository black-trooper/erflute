package org.dbflute.erflute.editor.controller.editpolicy.not_element.group;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.erflute.editor.controller.command.diagram_contents.not_element.group.ChangeColumnGroupCommand;
import org.dbflute.erflute.editor.controller.editpolicy.not_element.NotElementComponentEditPolicy;
import org.dbflute.erflute.editor.model.ERDiagram;
import org.dbflute.erflute.editor.model.diagram_contents.not_element.group.ColumnGroup;
import org.dbflute.erflute.editor.model.diagram_contents.not_element.group.CopyGroup;
import org.eclipse.gef.commands.Command;

public class GroupComponentEditPolicy extends NotElementComponentEditPolicy {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command createDeleteCommand(ERDiagram diagram, Object model) {
        ColumnGroup deleteColumnGroup = (ColumnGroup) model;

        List<CopyGroup> newColumnGroups = new ArrayList<CopyGroup>();

        for (ColumnGroup columnGroup : diagram.getDiagramContents().getColumnGroupSet()) {
            if (columnGroup != deleteColumnGroup) {
                newColumnGroups.add(new CopyGroup(columnGroup));
            }
        }

        return new ChangeColumnGroupCommand(diagram, diagram.getDiagramContents().getColumnGroupSet(), newColumnGroups);
    }

}
