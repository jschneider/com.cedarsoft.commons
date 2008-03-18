package eu.cedarsoft.presenter.demo.avat;

import java.util.ArrayList;
import java.util.List;

public class Group {
  private String name;
  private String icon;
  private String command;
  private Mask mask = new Mask();
  private List<Mask> masks = new ArrayList<Mask>();
  private List<SubGroup> subGroups = new ArrayList<SubGroup>();

  public void addMask( Mask additionalMask ) {
    masks.add( additionalMask );
  }

  public void removeMask( Mask mask ) {
    masks.remove( mask );
  }

  public void addSubGroup( SubGroup subGroup ) {
    subGroups.add( subGroup );
  }

  public void removeSubGroup( SubGroup subGroup ) {
    subGroups.remove( subGroup );
  }

  public String getCommand() {
    return command;
  }

  public void setCommand( String command ) {
    this.command = command;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon( String icon ) {
    this.icon = icon;
  }

  public Mask getMask() {
    return mask;
  }

  public void setMask( Mask mask ) {
    this.mask = mask;
  }

  public List<Mask> getMasks() {
    return masks;
  }

  public void setMasks( List<Mask> masks ) {
    this.masks = masks;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public List<SubGroup> getSubGroups() {
    return subGroups;
  }

  public void setSubGroups( List<SubGroup> subGroups ) {
    this.subGroups = subGroups;
  }
}