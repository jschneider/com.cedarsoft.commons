package com.cedarsoft.presenter.demo.avat;

import java.util.ArrayList;
import java.util.List;

public class SubGroup {
  private String name;
  private String text;
  private String command;
  private List<Mask> masks = new ArrayList<Mask>();

  public void addMask( Mask mask ) {
    masks.add( mask );
  }

  public void removeMask( Mask mask ) {
    masks.remove( mask );
  }

  public String getCommand() {
    return command;
  }

  public void setCommand( String command ) {
    this.command = command;
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

  public String getText() {
    return text;
  }

  public void setText( String text ) {
    this.text = text;
  }
}
