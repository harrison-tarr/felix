/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless
 * required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations
 * under the License.
 */
package org.apache.felix.dm;

import org.osgi.framework.Bundle;

public interface BundleDependency extends Dependency, ComponentDependencyDeclaration
{
  /**
   * Sets the callbacks for this dependency. These callbacks can be used as hooks whenever a
   * dependency is added or removed. When you specify callbacks, the auto configuration feature
   * is automatically turned off, because we're assuming you don't need it in this case.
   * 
   * @param added the method to call when a bundle was added
   * @param removed the method to call when a bundle was removed
   * @return this bundle dependency
   */
  BundleDependency setCallbacks(String added, String removed);

  /**
   * Sets the callbacks for this dependency. These callbacks can be used as hooks whenever a
   * dependency is added, changed or removed. When you specify callbacks, the auto configuration
   * feature is automatically turned off, because we're assuming you don't need it in this case.
   * 
   * @param added the method to call when a bundle was added
   * @param changed the method to call when a bundle was changed
   * @param removed the method to call when a bundle was removed
   * @return this bundle dependency
   */
  BundleDependency setCallbacks(String added, String changed, String removed);

  /**
   * Sets the callbacks for this dependency. These callbacks can be used as hooks whenever a
   * dependency is added or removed. They are called on the instance you provide. When you
   * specify callbacks, the auto configuration feature is automatically turned off, because
   * we're assuming you don't need it in this case.
   * 
   * @param instance the instance to call the callbacks on
   * @param added the method to call when a bundle was added
   * @param removed the method to call when a bundle was removed
   * @return this bundle dependency
   */
  BundleDependency setCallbacks(Object instance, String added, String removed);

  /**
   * Sets the callbacks for this dependency. These callbacks can be used as hooks whenever a
   * dependency is added, changed or removed. They are called on the instance you provide. When
   * you specify callbacks, the auto configuration feature is automatically turned off, because
   * we're assuming you don't need it in this case.
   * 
   * @param instance the instance to call the callbacks on
   * @param added the method to call when a bundle was added
   * @param changed the method to call when a bundle was changed
   * @param removed the method to call when a bundle was removed
   * @return this service dependency
   */
  BundleDependency setCallbacks(Object instance, String added, String changed, String removed);

  BundleDependency setAutoConfig(boolean autoConfig);

  BundleDependency setRequired(boolean required);

  BundleDependency setBundle(Bundle bundle);

  BundleDependency setFilter(String filter) throws IllegalArgumentException;

  BundleDependency setStateMask(int mask);
  
  BundleDependency setPropagate(boolean propagate);
  
  BundleDependency setInstanceBound(boolean isInstanceBound);

}
