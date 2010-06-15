/**
 * Copyright (C) cedarsoft GmbH.
 *
 * Licensed under the GNU General Public License version 3 (the "License")
 * with Classpath Exception; you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *         http://www.cedarsoft.org/gpl3ce
 *         (GPL 3 with Classpath Exception)
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation. cedarsoft GmbH designates this
 * particular file as subject to the "Classpath" exception as provided
 * by cedarsoft GmbH in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact cedarsoft GmbH, 72810 Gomaringen, Germany,
 * or visit www.cedarsoft.com if you need additional information or
 * have any questions.
 */

package com.cedarsoft.workflow;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Represents a state within the workflow
 *
 * @author Johannes Schneider (<a href=mailto:js@cedarsoft.com>js@cedarsoft.com</a>)
 */
public class State<T> {
  @NotNull
  @NonNls
  private final String id;

  @NotNull
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  @NotNull
  private final List<TransitionDefinition<T>> transitionDefinitions = new ArrayList<TransitionDefinition<T>>();

  /**
   * Creates a new state
   *
   * @param id  the id
   */
  public State( @NotNull @NonNls String id ) {
    this.id = id;
  }

  /**
   * Returns the id
   *
   * @return the id
   */
  @NotNull
  public String getId() {
    return id;
  }

  /**
   * Creates a new transition definition for the given target state and action
   *
   * @param targetState the target state
   * @param actions     the actions
   * @return the definition
   */
  @NotNull
  public TransitionDefinition<T> createTransition( @NotNull State<T> targetState, @NotNull List<? extends Action<T>> actions ) {
    lock.writeLock().lock();
    try {
      TransitionDefinition<T> transitionDefinition = new TransitionDefinition<T>( this, targetState, actions );
      transitionDefinitions.add( transitionDefinition );
      return transitionDefinition;
    } finally {
      lock.writeLock().unlock();
    }
  }

  /**
   * <p>createTransition</p>
   *
   * @param targetState a {@link com.cedarsoft.workflow.State} object.
   * @param actions     a {@link com.cedarsoft.workflow.Action} object.
   * @return a {@link com.cedarsoft.workflow.TransitionDefinition} object.
   */
  @NotNull
  public TransitionDefinition<T> createTransition( @NotNull State<T> targetState, @NotNull Action<T>... actions ) {
    return createTransition( targetState, Arrays.asList( actions ) );
  }

  /**
   * Returns the transitions for this state
   *
   * @return the transitions
   */
  @NotNull
  public List<? extends TransitionDefinition<T>> getTransitions() {
    lock.readLock().lock();
    try {
      return Collections.unmodifiableList( transitionDefinitions );
    } finally {
      lock.readLock().unlock();
    }
  }

  private class EmptyAction<T> implements Action<T> {
    @Override
    public void execute( @NotNull T object, @NotNull TransitionDefinition<T> definition ) {
    }
  }
}
