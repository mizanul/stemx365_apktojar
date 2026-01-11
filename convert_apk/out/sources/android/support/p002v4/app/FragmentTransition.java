package android.support.p002v4.app;

import android.graphics.Rect;
import android.os.Build;
import android.support.p002v4.util.ArrayMap;
import android.support.p002v4.view.ViewCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* renamed from: android.support.v4.app.FragmentTransition */
class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8};

    FragmentTransition() {
    }

    static void startTransitions(FragmentManagerImpl fragmentManager, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex, boolean isReordered) {
        if (fragmentManager.mCurState >= 1 && Build.VERSION.SDK_INT >= 21) {
            SparseArray<FragmentContainerTransition> transitioningFragments = new SparseArray<>();
            for (int i = startIndex; i < endIndex; i++) {
                BackStackRecord record = records.get(i);
                if (isRecordPop.get(i).booleanValue()) {
                    calculatePopFragments(record, transitioningFragments, isReordered);
                } else {
                    calculateFragments(record, transitioningFragments, isReordered);
                }
            }
            if (transitioningFragments.size() != 0) {
                View nonExistentView = new View(fragmentManager.mHost.getContext());
                int numContainers = transitioningFragments.size();
                for (int i2 = 0; i2 < numContainers; i2++) {
                    int containerId = transitioningFragments.keyAt(i2);
                    ArrayMap<String, String> nameOverrides = calculateNameOverrides(containerId, records, isRecordPop, startIndex, endIndex);
                    FragmentContainerTransition containerTransition = transitioningFragments.valueAt(i2);
                    if (isReordered) {
                        configureTransitionsReordered(fragmentManager, containerId, containerTransition, nonExistentView, nameOverrides);
                    } else {
                        configureTransitionsOrdered(fragmentManager, containerId, containerTransition, nonExistentView, nameOverrides);
                    }
                }
            }
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int containerId, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        ArrayList<String> sources;
        ArrayList<String> targets;
        ArrayMap<String, String> nameOverrides = new ArrayMap<>();
        for (int recordNum = endIndex - 1; recordNum >= startIndex; recordNum--) {
            BackStackRecord record = records.get(recordNum);
            if (record.interactsWith(containerId)) {
                boolean isPop = isRecordPop.get(recordNum).booleanValue();
                if (record.mSharedElementSourceNames != null) {
                    int numSharedElements = record.mSharedElementSourceNames.size();
                    if (isPop) {
                        targets = record.mSharedElementSourceNames;
                        sources = record.mSharedElementTargetNames;
                    } else {
                        sources = record.mSharedElementSourceNames;
                        targets = record.mSharedElementTargetNames;
                    }
                    for (int i = 0; i < numSharedElements; i++) {
                        String sourceName = sources.get(i);
                        String targetName = targets.get(i);
                        String previousTarget = nameOverrides.remove(targetName);
                        if (previousTarget != null) {
                            nameOverrides.put(sourceName, previousTarget);
                        } else {
                            nameOverrides.put(sourceName, targetName);
                        }
                    }
                }
            }
        }
        return nameOverrides;
    }

    /* JADX WARNING: type inference failed for: r2v6, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void configureTransitionsReordered(android.support.p002v4.app.FragmentManagerImpl r26, int r27, android.support.p002v4.app.FragmentTransition.FragmentContainerTransition r28, android.view.View r29, android.support.p002v4.util.ArrayMap<java.lang.String, java.lang.String> r30) {
        /*
            r0 = r26
            r9 = r28
            r10 = r29
            r1 = 0
            android.support.v4.app.FragmentContainer r2 = r0.mContainer
            boolean r2 = r2.onHasView()
            if (r2 == 0) goto L_0x001c
            android.support.v4.app.FragmentContainer r2 = r0.mContainer
            r11 = r27
            android.view.View r2 = r2.onFindViewById(r11)
            r1 = r2
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            r12 = r1
            goto L_0x001f
        L_0x001c:
            r11 = r27
            r12 = r1
        L_0x001f:
            if (r12 != 0) goto L_0x0022
            return
        L_0x0022:
            android.support.v4.app.Fragment r13 = r9.lastIn
            android.support.v4.app.Fragment r14 = r9.firstOut
            boolean r15 = r9.lastInIsPop
            boolean r8 = r9.firstOutIsPop
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r7 = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r6 = r1
            java.lang.Object r5 = getEnterTransition(r13, r15)
            java.lang.Object r4 = getExitTransition(r14, r8)
            r1 = r12
            r2 = r29
            r3 = r30
            r23 = r4
            r4 = r28
            r24 = r5
            r5 = r6
            r0 = r6
            r6 = r7
            r9 = r7
            r7 = r24
            r25 = r8
            r8 = r23
            java.lang.Object r1 = configureSharedElementsReordered(r1, r2, r3, r4, r5, r6, r7, r8)
            r2 = r24
            if (r2 != 0) goto L_0x0062
            if (r1 != 0) goto L_0x0062
            r3 = r23
            if (r3 != 0) goto L_0x0064
            return
        L_0x0062:
            r3 = r23
        L_0x0064:
            java.util.ArrayList r4 = configureEnteringExitingViews(r3, r14, r0, r10)
            java.util.ArrayList r5 = configureEnteringExitingViews(r2, r13, r9, r10)
            r6 = 4
            setViewVisibility(r5, r6)
            java.lang.Object r6 = mergeTransitions(r2, r3, r1, r13, r15)
            if (r6 == 0) goto L_0x00a1
            replaceHide(r3, r14, r4)
            java.util.ArrayList r7 = android.support.p002v4.app.FragmentTransitionCompat21.prepareSetNameOverridesReordered(r9)
            r16 = r6
            r17 = r2
            r18 = r5
            r19 = r3
            r20 = r4
            r21 = r1
            r22 = r9
            android.support.p002v4.app.FragmentTransitionCompat21.scheduleRemoveTargets(r16, r17, r18, r19, r20, r21, r22)
            android.support.p002v4.app.FragmentTransitionCompat21.beginDelayedTransition(r12, r6)
            r8 = r30
            android.support.p002v4.app.FragmentTransitionCompat21.setNameOverridesReordered(r12, r0, r9, r7, r8)
            r24 = r2
            r2 = 0
            setViewVisibility(r5, r2)
            android.support.p002v4.app.FragmentTransitionCompat21.swapSharedElementTargets(r1, r0, r9)
            goto L_0x00a5
        L_0x00a1:
            r8 = r30
            r24 = r2
        L_0x00a5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p002v4.app.FragmentTransition.configureTransitionsReordered(android.support.v4.app.FragmentManagerImpl, int, android.support.v4.app.FragmentTransition$FragmentContainerTransition, android.view.View, android.support.v4.util.ArrayMap):void");
    }

    private static void replaceHide(Object exitTransition, Fragment exitingFragment, final ArrayList<View> exitingViews) {
        if (exitingFragment != null && exitTransition != null && exitingFragment.mAdded && exitingFragment.mHidden && exitingFragment.mHiddenChanged) {
            exitingFragment.setHideReplaced(true);
            FragmentTransitionCompat21.scheduleHideFragmentView(exitTransition, exitingFragment.getView(), exitingViews);
            OneShotPreDrawListener.add(exitingFragment.mContainer, new Runnable() {
                public void run() {
                    FragmentTransition.setViewVisibility(exitingViews, 4);
                }
            });
        }
    }

    /* JADX WARNING: type inference failed for: r2v6, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void configureTransitionsOrdered(android.support.p002v4.app.FragmentManagerImpl r28, int r29, android.support.p002v4.app.FragmentTransition.FragmentContainerTransition r30, android.view.View r31, android.support.p002v4.util.ArrayMap<java.lang.String, java.lang.String> r32) {
        /*
            r0 = r28
            r9 = r30
            r10 = r31
            r11 = r32
            r1 = 0
            android.support.v4.app.FragmentContainer r2 = r0.mContainer
            boolean r2 = r2.onHasView()
            if (r2 == 0) goto L_0x001e
            android.support.v4.app.FragmentContainer r2 = r0.mContainer
            r12 = r29
            android.view.View r2 = r2.onFindViewById(r12)
            r1 = r2
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            r13 = r1
            goto L_0x0021
        L_0x001e:
            r12 = r29
            r13 = r1
        L_0x0021:
            if (r13 != 0) goto L_0x0024
            return
        L_0x0024:
            android.support.v4.app.Fragment r14 = r9.lastIn
            android.support.v4.app.Fragment r15 = r9.firstOut
            boolean r8 = r9.lastInIsPop
            boolean r7 = r9.firstOutIsPop
            java.lang.Object r6 = getEnterTransition(r14, r8)
            java.lang.Object r5 = getExitTransition(r15, r7)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r4 = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r3 = r1
            r1 = r13
            r2 = r31
            r23 = r3
            r3 = r32
            r24 = r4
            r4 = r30
            r16 = r5
            r5 = r24
            r25 = r6
            r6 = r23
            r26 = r7
            r7 = r25
            r27 = r8
            r8 = r16
            java.lang.Object r8 = configureSharedElementsOrdered(r1, r2, r3, r4, r5, r6, r7, r8)
            if (r7 != 0) goto L_0x0069
            if (r8 != 0) goto L_0x0069
            r1 = r16
            if (r1 != 0) goto L_0x006b
            return
        L_0x0069:
            r1 = r16
        L_0x006b:
            r6 = r24
            java.util.ArrayList r24 = configureEnteringExitingViews(r1, r15, r6, r10)
            if (r24 == 0) goto L_0x007c
            boolean r2 = r24.isEmpty()
            if (r2 == 0) goto L_0x007a
            goto L_0x007c
        L_0x007a:
            r5 = r1
            goto L_0x007d
        L_0x007c:
            r5 = 0
        L_0x007d:
            android.support.p002v4.app.FragmentTransitionCompat21.addTarget(r7, r10)
            boolean r1 = r9.lastInIsPop
            java.lang.Object r4 = mergeTransitions(r7, r5, r8, r14, r1)
            if (r4 == 0) goto L_0x00c1
            java.util.ArrayList r18 = new java.util.ArrayList
            r18.<init>()
            r16 = r4
            r17 = r7
            r19 = r5
            r20 = r24
            r21 = r8
            r22 = r23
            android.support.p002v4.app.FragmentTransitionCompat21.scheduleRemoveTargets(r16, r17, r18, r19, r20, r21, r22)
            r1 = r13
            r2 = r14
            r3 = r31
            r0 = r4
            r4 = r23
            r16 = r5
            r5 = r7
            r17 = r6
            r6 = r18
            r19 = r7
            r7 = r16
            r20 = r8
            r8 = r24
            scheduleTargetChange(r1, r2, r3, r4, r5, r6, r7, r8)
            r1 = r23
            android.support.p002v4.app.FragmentTransitionCompat21.setNameOverridesOrdered(r13, r1, r11)
            android.support.p002v4.app.FragmentTransitionCompat21.beginDelayedTransition(r13, r0)
            android.support.p002v4.app.FragmentTransitionCompat21.scheduleNameReset(r13, r1, r11)
            goto L_0x00cc
        L_0x00c1:
            r0 = r4
            r16 = r5
            r17 = r6
            r19 = r7
            r20 = r8
            r1 = r23
        L_0x00cc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p002v4.app.FragmentTransition.configureTransitionsOrdered(android.support.v4.app.FragmentManagerImpl, int, android.support.v4.app.FragmentTransition$FragmentContainerTransition, android.view.View, android.support.v4.util.ArrayMap):void");
    }

    private static void scheduleTargetChange(ViewGroup sceneRoot, Fragment inFragment, View nonExistentView, ArrayList<View> sharedElementsIn, Object enterTransition, ArrayList<View> enteringViews, Object exitTransition, ArrayList<View> exitingViews) {
        final Object obj = enterTransition;
        final View view = nonExistentView;
        final Fragment fragment = inFragment;
        final ArrayList<View> arrayList = sharedElementsIn;
        final ArrayList<View> arrayList2 = enteringViews;
        final ArrayList<View> arrayList3 = exitingViews;
        final Object obj2 = exitTransition;
        ViewGroup viewGroup = sceneRoot;
        OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            public void run() {
                Object obj = obj;
                if (obj != null) {
                    FragmentTransitionCompat21.removeTarget(obj, view);
                    arrayList2.addAll(FragmentTransition.configureEnteringExitingViews(obj, fragment, arrayList, view));
                }
                if (arrayList3 != null) {
                    if (obj2 != null) {
                        ArrayList<View> tempExiting = new ArrayList<>();
                        tempExiting.add(view);
                        FragmentTransitionCompat21.replaceTargets(obj2, arrayList3, tempExiting);
                    }
                    arrayList3.clear();
                    arrayList3.add(view);
                }
            }
        });
    }

    private static Object getSharedElementTransition(Fragment inFragment, Fragment outFragment, boolean isPop) {
        Object obj;
        if (inFragment == null || outFragment == null) {
            return null;
        }
        if (isPop) {
            obj = outFragment.getSharedElementReturnTransition();
        } else {
            obj = inFragment.getSharedElementEnterTransition();
        }
        return FragmentTransitionCompat21.wrapTransitionInSet(FragmentTransitionCompat21.cloneTransition(obj));
    }

    private static Object getEnterTransition(Fragment inFragment, boolean isPop) {
        Object obj;
        if (inFragment == null) {
            return null;
        }
        if (isPop) {
            obj = inFragment.getReenterTransition();
        } else {
            obj = inFragment.getEnterTransition();
        }
        return FragmentTransitionCompat21.cloneTransition(obj);
    }

    private static Object getExitTransition(Fragment outFragment, boolean isPop) {
        Object obj;
        if (outFragment == null) {
            return null;
        }
        if (isPop) {
            obj = outFragment.getReturnTransition();
        } else {
            obj = outFragment.getExitTransition();
        }
        return FragmentTransitionCompat21.cloneTransition(obj);
    }

    private static Object configureSharedElementsReordered(ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        Object sharedElementTransition2;
        View epicenterView;
        Rect epicenter;
        View view = nonExistentView;
        ArrayMap<String, String> arrayMap = nameOverrides;
        FragmentContainerTransition fragmentContainerTransition = fragments;
        ArrayList<View> arrayList = sharedElementsOut;
        ArrayList<View> arrayList2 = sharedElementsIn;
        Object obj = enterTransition;
        Object obj2 = exitTransition;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        Fragment outFragment = fragmentContainerTransition.firstOut;
        if (inFragment != null) {
            inFragment.getView().setVisibility(0);
        }
        if (inFragment == null) {
            ViewGroup viewGroup = sceneRoot;
        } else if (outFragment == null) {
            ViewGroup viewGroup2 = sceneRoot;
        } else {
            boolean inIsPop = fragmentContainerTransition.lastInIsPop;
            if (nameOverrides.isEmpty()) {
                sharedElementTransition = null;
            } else {
                sharedElementTransition = getSharedElementTransition(inFragment, outFragment, inIsPop);
            }
            ArrayMap<String, View> outSharedElements = captureOutSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
            final ArrayMap<String, View> inSharedElements = captureInSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
            if (nameOverrides.isEmpty()) {
                if (outSharedElements != null) {
                    outSharedElements.clear();
                }
                if (inSharedElements != null) {
                    inSharedElements.clear();
                }
                sharedElementTransition2 = null;
            } else {
                addSharedElementsWithMatchingNames(arrayList, outSharedElements, nameOverrides.keySet());
                addSharedElementsWithMatchingNames(arrayList2, inSharedElements, nameOverrides.values());
                sharedElementTransition2 = sharedElementTransition;
            }
            if (obj == null && obj2 == null && sharedElementTransition2 == null) {
                return null;
            }
            callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
            if (sharedElementTransition2 != null) {
                arrayList2.add(view);
                FragmentTransitionCompat21.setSharedElementTargets(sharedElementTransition2, view, arrayList);
                setOutEpicenter(sharedElementTransition2, obj2, outSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                Rect epicenter2 = new Rect();
                View epicenterView2 = getInEpicenterView(inSharedElements, fragmentContainerTransition, obj, inIsPop);
                if (epicenterView2 != null) {
                    FragmentTransitionCompat21.setEpicenter(obj, epicenter2);
                }
                epicenterView = epicenterView2;
                epicenter = epicenter2;
            } else {
                epicenterView = null;
                epicenter = null;
            }
            final Fragment fragment = inFragment;
            C01103 r0 = r7;
            final Fragment fragment2 = outFragment;
            Object sharedElementTransition3 = sharedElementTransition2;
            final boolean z = inIsPop;
            ArrayMap<String, View> arrayMap2 = inSharedElements;
            ArrayMap<String, View> arrayMap3 = outSharedElements;
            final View view2 = epicenterView;
            boolean z2 = inIsPop;
            final Rect rect = epicenter;
            C01103 r7 = new Runnable() {
                public void run() {
                    FragmentTransition.callSharedElementStartEnd(fragment, fragment2, z, inSharedElements, false);
                    View view = view2;
                    if (view != null) {
                        FragmentTransitionCompat21.getBoundsOnScreen(view, rect);
                    }
                }
            };
            OneShotPreDrawListener.add(sceneRoot, r0);
            return sharedElementTransition3;
        }
        return null;
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> views, ArrayMap<String, View> sharedElements, Collection<String> nameOverridesSet) {
        for (int i = sharedElements.size() - 1; i >= 0; i--) {
            View view = sharedElements.valueAt(i);
            if (nameOverridesSet.contains(ViewCompat.getTransitionName(view))) {
                views.add(view);
            }
        }
    }

    private static Object configureSharedElementsOrdered(ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        Object sharedElementTransition2;
        Rect inEpicenter;
        FragmentContainerTransition fragmentContainerTransition = fragments;
        ArrayList<View> arrayList = sharedElementsOut;
        Object obj = enterTransition;
        Object obj2 = exitTransition;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        Fragment outFragment = fragmentContainerTransition.firstOut;
        if (inFragment == null) {
            ViewGroup viewGroup = sceneRoot;
            Fragment fragment = outFragment;
            Fragment fragment2 = inFragment;
        } else if (outFragment == null) {
            ViewGroup viewGroup2 = sceneRoot;
            Fragment fragment3 = outFragment;
            Fragment fragment4 = inFragment;
        } else {
            boolean inIsPop = fragmentContainerTransition.lastInIsPop;
            if (nameOverrides.isEmpty()) {
                sharedElementTransition = null;
            } else {
                sharedElementTransition = getSharedElementTransition(inFragment, outFragment, inIsPop);
            }
            ArrayMap<String, View> outSharedElements = captureOutSharedElements(nameOverrides, sharedElementTransition, fragmentContainerTransition);
            if (nameOverrides.isEmpty()) {
                sharedElementTransition2 = null;
            } else {
                arrayList.addAll(outSharedElements.values());
                sharedElementTransition2 = sharedElementTransition;
            }
            if (obj == null && obj2 == null && sharedElementTransition2 == null) {
                return null;
            }
            callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
            if (sharedElementTransition2 != null) {
                Rect inEpicenter2 = new Rect();
                FragmentTransitionCompat21.setSharedElementTargets(sharedElementTransition2, nonExistentView, arrayList);
                setOutEpicenter(sharedElementTransition2, obj2, outSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                if (obj != null) {
                    FragmentTransitionCompat21.setEpicenter(obj, inEpicenter2);
                }
                inEpicenter = inEpicenter2;
            } else {
                View view = nonExistentView;
                inEpicenter = null;
            }
            final Object finalSharedElementTransition = sharedElementTransition2;
            final ArrayMap<String, String> arrayMap = nameOverrides;
            final FragmentContainerTransition fragmentContainerTransition2 = fragments;
            C01114 r12 = r0;
            final ArrayList<View> arrayList2 = sharedElementsIn;
            final View view2 = nonExistentView;
            Object sharedElementTransition3 = sharedElementTransition2;
            final Fragment fragment5 = inFragment;
            ArrayMap<String, View> arrayMap2 = outSharedElements;
            final Fragment fragment6 = outFragment;
            final boolean z = inIsPop;
            boolean z2 = inIsPop;
            final ArrayList<View> arrayList3 = sharedElementsOut;
            Fragment fragment7 = outFragment;
            final Object obj3 = enterTransition;
            Fragment fragment8 = inFragment;
            final Rect rect = inEpicenter;
            C01114 r0 = new Runnable() {
                public void run() {
                    ArrayMap<String, View> inSharedElements = FragmentTransition.captureInSharedElements(arrayMap, finalSharedElementTransition, fragmentContainerTransition2);
                    if (inSharedElements != null) {
                        arrayList2.addAll(inSharedElements.values());
                        arrayList2.add(view2);
                    }
                    FragmentTransition.callSharedElementStartEnd(fragment5, fragment6, z, inSharedElements, false);
                    Object obj = finalSharedElementTransition;
                    if (obj != null) {
                        FragmentTransitionCompat21.swapSharedElementTargets(obj, arrayList3, arrayList2);
                        View inEpicenterView = FragmentTransition.getInEpicenterView(inSharedElements, fragmentContainerTransition2, obj3, z);
                        if (inEpicenterView != null) {
                            FragmentTransitionCompat21.getBoundsOnScreen(inEpicenterView, rect);
                        }
                    }
                }
            };
            OneShotPreDrawListener.add(sceneRoot, r12);
            return sharedElementTransition3;
        }
        return null;
    }

    private static ArrayMap<String, View> captureOutSharedElements(ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        ArrayList<String> names;
        SharedElementCallback sharedElementCallback;
        if (nameOverrides.isEmpty() || sharedElementTransition == null) {
            nameOverrides.clear();
            return null;
        }
        Fragment outFragment = fragments.firstOut;
        ArrayMap<String, View> outSharedElements = new ArrayMap<>();
        FragmentTransitionCompat21.findNamedViews(outSharedElements, outFragment.getView());
        BackStackRecord outTransaction = fragments.firstOutTransaction;
        if (fragments.firstOutIsPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
            names = outTransaction.mSharedElementTargetNames;
        } else {
            sharedElementCallback = outFragment.getExitTransitionCallback();
            names = outTransaction.mSharedElementSourceNames;
        }
        outSharedElements.retainAll(names);
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, outSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = names.get(i);
                View view = outSharedElements.get(name);
                if (view == null) {
                    nameOverrides.remove(name);
                } else if (!name.equals(ViewCompat.getTransitionName(view))) {
                    nameOverrides.put(ViewCompat.getTransitionName(view), nameOverrides.remove(name));
                }
            }
        } else {
            nameOverrides.retainAll(outSharedElements.keySet());
        }
        return outSharedElements;
    }

    /* access modifiers changed from: private */
    public static ArrayMap<String, View> captureInSharedElements(ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        ArrayList<String> names;
        SharedElementCallback sharedElementCallback;
        String key;
        Fragment inFragment = fragments.lastIn;
        View fragmentView = inFragment.getView();
        if (nameOverrides.isEmpty() || sharedElementTransition == null || fragmentView == null) {
            nameOverrides.clear();
            return null;
        }
        ArrayMap<String, View> inSharedElements = new ArrayMap<>();
        FragmentTransitionCompat21.findNamedViews(inSharedElements, fragmentView);
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (fragments.lastInIsPop) {
            sharedElementCallback = inFragment.getExitTransitionCallback();
            names = inTransaction.mSharedElementSourceNames;
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
            names = inTransaction.mSharedElementTargetNames;
        }
        if (names != null) {
            inSharedElements.retainAll(names);
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, inSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = names.get(i);
                View view = inSharedElements.get(name);
                if (view == null) {
                    String key2 = findKeyForValue(nameOverrides, name);
                    if (key2 != null) {
                        nameOverrides.remove(key2);
                    }
                } else if (!name.equals(ViewCompat.getTransitionName(view)) && (key = findKeyForValue(nameOverrides, name)) != null) {
                    nameOverrides.put(key, ViewCompat.getTransitionName(view));
                }
            }
        } else {
            retainValues(nameOverrides, inSharedElements);
        }
        return inSharedElements;
    }

    private static String findKeyForValue(ArrayMap<String, String> map, String value) {
        int numElements = map.size();
        for (int i = 0; i < numElements; i++) {
            if (value.equals(map.valueAt(i))) {
                return map.keyAt(i);
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static View getInEpicenterView(ArrayMap<String, View> inSharedElements, FragmentContainerTransition fragments, Object enterTransition, boolean inIsPop) {
        String targetName;
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (enterTransition == null || inSharedElements == null || inTransaction.mSharedElementSourceNames == null || inTransaction.mSharedElementSourceNames.isEmpty()) {
            return null;
        }
        if (inIsPop) {
            targetName = inTransaction.mSharedElementSourceNames.get(0);
        } else {
            targetName = inTransaction.mSharedElementTargetNames.get(0);
        }
        return inSharedElements.get(targetName);
    }

    private static void setOutEpicenter(Object sharedElementTransition, Object exitTransition, ArrayMap<String, View> outSharedElements, boolean outIsPop, BackStackRecord outTransaction) {
        String sourceName;
        if (outTransaction.mSharedElementSourceNames != null && !outTransaction.mSharedElementSourceNames.isEmpty()) {
            if (outIsPop) {
                sourceName = outTransaction.mSharedElementTargetNames.get(0);
            } else {
                sourceName = outTransaction.mSharedElementSourceNames.get(0);
            }
            View outEpicenterView = outSharedElements.get(sourceName);
            FragmentTransitionCompat21.setEpicenter(sharedElementTransition, outEpicenterView);
            if (exitTransition != null) {
                FragmentTransitionCompat21.setEpicenter(exitTransition, outEpicenterView);
            }
        }
    }

    private static void retainValues(ArrayMap<String, String> nameOverrides, ArrayMap<String, View> namedViews) {
        for (int i = nameOverrides.size() - 1; i >= 0; i--) {
            if (!namedViews.containsKey(nameOverrides.valueAt(i))) {
                nameOverrides.removeAt(i);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void callSharedElementStartEnd(Fragment inFragment, Fragment outFragment, boolean isPop, ArrayMap<String, View> sharedElements, boolean isStart) {
        SharedElementCallback sharedElementCallback;
        if (isPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
        }
        if (sharedElementCallback != null) {
            ArrayList<View> views = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            int count = sharedElements == null ? 0 : sharedElements.size();
            for (int i = 0; i < count; i++) {
                names.add(sharedElements.keyAt(i));
                views.add(sharedElements.valueAt(i));
            }
            if (isStart) {
                sharedElementCallback.onSharedElementStart(names, views, (List<View>) null);
            } else {
                sharedElementCallback.onSharedElementEnd(names, views, (List<View>) null);
            }
        }
    }

    /* access modifiers changed from: private */
    public static ArrayList<View> configureEnteringExitingViews(Object transition, Fragment fragment, ArrayList<View> sharedElements, View nonExistentView) {
        ArrayList<View> viewList = null;
        if (transition != null) {
            viewList = new ArrayList<>();
            View root = fragment.getView();
            if (root != null) {
                FragmentTransitionCompat21.captureTransitioningViews(viewList, root);
            }
            if (sharedElements != null) {
                viewList.removeAll(sharedElements);
            }
            if (!viewList.isEmpty()) {
                viewList.add(nonExistentView);
                FragmentTransitionCompat21.addTargets(transition, viewList);
            }
        }
        return viewList;
    }

    /* access modifiers changed from: private */
    public static void setViewVisibility(ArrayList<View> views, int visibility) {
        if (views != null) {
            for (int i = views.size() - 1; i >= 0; i--) {
                views.get(i).setVisibility(visibility);
            }
        }
    }

    private static Object mergeTransitions(Object enterTransition, Object exitTransition, Object sharedElementTransition, Fragment inFragment, boolean isPop) {
        boolean z;
        boolean overlap = true;
        if (!(enterTransition == null || exitTransition == null || inFragment == null)) {
            if (isPop) {
                z = inFragment.getAllowReturnTransitionOverlap();
            } else {
                z = inFragment.getAllowEnterTransitionOverlap();
            }
            overlap = z;
        }
        if (overlap) {
            return FragmentTransitionCompat21.mergeTransitionsTogether(exitTransition, enterTransition, sharedElementTransition);
        }
        return FragmentTransitionCompat21.mergeTransitionsInSequence(exitTransition, enterTransition, sharedElementTransition);
    }

    public static void calculateFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        int numOps = transaction.mOps.size();
        for (int opNum = 0; opNum < numOps; opNum++) {
            addToFirstInLastOut(transaction, transaction.mOps.get(opNum), transitioningFragments, false, isReordered);
        }
    }

    public static void calculatePopFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        if (transaction.mManager.mContainer.onHasView()) {
            for (int opNum = transaction.mOps.size() - 1; opNum >= 0; opNum--) {
                addToFirstInLastOut(transaction, transaction.mOps.get(opNum), transitioningFragments, true, isReordered);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x012f A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:111:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x00c5  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00d6 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0116  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void addToFirstInLastOut(android.support.p002v4.app.BackStackRecord r22, android.support.p002v4.app.BackStackRecord.C0094Op r23, android.util.SparseArray<android.support.p002v4.app.FragmentTransition.FragmentContainerTransition> r24, boolean r25, boolean r26) {
        /*
            r0 = r22
            r1 = r23
            r2 = r24
            r3 = r25
            android.support.v4.app.Fragment r10 = r1.fragment
            if (r10 != 0) goto L_0x000d
            return
        L_0x000d:
            int r11 = r10.mContainerId
            if (r11 != 0) goto L_0x0012
            return
        L_0x0012:
            if (r3 == 0) goto L_0x001b
            int[] r4 = INVERSE_OPS
            int r5 = r1.cmd
            r4 = r4[r5]
            goto L_0x001d
        L_0x001b:
            int r4 = r1.cmd
        L_0x001d:
            r12 = r4
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 1
            if (r12 == r9) goto L_0x00a8
            r13 = 3
            if (r12 == r13) goto L_0x0079
            r13 = 4
            if (r12 == r13) goto L_0x0057
            r13 = 5
            if (r12 == r13) goto L_0x003c
            r13 = 6
            if (r12 == r13) goto L_0x0079
            r13 = 7
            if (r12 == r13) goto L_0x00a8
            r13 = r4
            r14 = r5
            r15 = r6
            r16 = r7
            goto L_0x00bd
        L_0x003c:
            if (r26 == 0) goto L_0x004d
            boolean r13 = r10.mHiddenChanged
            if (r13 == 0) goto L_0x004b
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x004b
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x004b
            r8 = r9
        L_0x004b:
            r4 = r8
            goto L_0x004f
        L_0x004d:
            boolean r4 = r10.mHidden
        L_0x004f:
            r7 = 1
            r13 = r4
            r14 = r5
            r15 = r6
            r16 = r7
            goto L_0x00bd
        L_0x0057:
            if (r26 == 0) goto L_0x0068
            boolean r13 = r10.mHiddenChanged
            if (r13 == 0) goto L_0x0066
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x0066
            boolean r13 = r10.mHidden
            if (r13 == 0) goto L_0x0066
            r8 = r9
        L_0x0066:
            r6 = r8
            goto L_0x0072
        L_0x0068:
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x0071
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x0071
            r8 = r9
        L_0x0071:
            r6 = r8
        L_0x0072:
            r5 = 1
            r13 = r4
            r14 = r5
            r15 = r6
            r16 = r7
            goto L_0x00bd
        L_0x0079:
            if (r26 == 0) goto L_0x0097
            boolean r13 = r10.mAdded
            if (r13 != 0) goto L_0x0094
            android.view.View r13 = r10.mView
            if (r13 == 0) goto L_0x0094
            android.view.View r13 = r10.mView
            int r13 = r13.getVisibility()
            if (r13 != 0) goto L_0x0094
            float r13 = r10.mPostponedAlpha
            r14 = 0
            int r13 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1))
            if (r13 < 0) goto L_0x0094
            r8 = r9
            goto L_0x0095
        L_0x0094:
        L_0x0095:
            r6 = r8
            goto L_0x00a1
        L_0x0097:
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x00a0
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x00a0
            r8 = r9
        L_0x00a0:
            r6 = r8
        L_0x00a1:
            r5 = 1
            r13 = r4
            r14 = r5
            r15 = r6
            r16 = r7
            goto L_0x00bd
        L_0x00a8:
            if (r26 == 0) goto L_0x00ad
            boolean r4 = r10.mIsNewlyAdded
            goto L_0x00b7
        L_0x00ad:
            boolean r13 = r10.mAdded
            if (r13 != 0) goto L_0x00b6
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x00b6
            r8 = r9
        L_0x00b6:
            r4 = r8
        L_0x00b7:
            r7 = 1
            r13 = r4
            r14 = r5
            r15 = r6
            r16 = r7
        L_0x00bd:
            java.lang.Object r4 = r2.get(r11)
            android.support.v4.app.FragmentTransition$FragmentContainerTransition r4 = (android.support.p002v4.app.FragmentTransition.FragmentContainerTransition) r4
            if (r13 == 0) goto L_0x00d2
            android.support.v4.app.FragmentTransition$FragmentContainerTransition r4 = ensureContainer(r4, r2, r11)
            r4.lastIn = r10
            r4.lastInIsPop = r3
            r4.lastInTransaction = r0
            r8 = r4
            goto L_0x00d3
        L_0x00d2:
            r8 = r4
        L_0x00d3:
            r7 = 0
            if (r26 != 0) goto L_0x0111
            if (r16 == 0) goto L_0x0111
            if (r8 == 0) goto L_0x00e0
            android.support.v4.app.Fragment r4 = r8.firstOut
            if (r4 != r10) goto L_0x00e0
            r8.firstOut = r7
        L_0x00e0:
            android.support.v4.app.FragmentManagerImpl r6 = r0.mManager
            int r4 = r10.mState
            if (r4 >= r9) goto L_0x010b
            int r4 = r6.mCurState
            if (r4 < r9) goto L_0x010b
            boolean r4 = r0.mReorderingAllowed
            if (r4 != 0) goto L_0x010b
            r6.makeActive(r10)
            r9 = 1
            r17 = 0
            r18 = 0
            r19 = 0
            r4 = r6
            r5 = r10
            r20 = r6
            r6 = r9
            r9 = r7
            r7 = r17
            r21 = r8
            r8 = r18
            r1 = r9
            r9 = r19
            r4.moveToState(r5, r6, r7, r8, r9)
            goto L_0x0114
        L_0x010b:
            r20 = r6
            r1 = r7
            r21 = r8
            goto L_0x0114
        L_0x0111:
            r1 = r7
            r21 = r8
        L_0x0114:
            if (r15 == 0) goto L_0x012a
            r4 = r21
            if (r4 == 0) goto L_0x011e
            android.support.v4.app.Fragment r5 = r4.firstOut
            if (r5 != 0) goto L_0x012c
        L_0x011e:
            android.support.v4.app.FragmentTransition$FragmentContainerTransition r8 = ensureContainer(r4, r2, r11)
            r8.firstOut = r10
            r8.firstOutIsPop = r3
            r8.firstOutTransaction = r0
            goto L_0x012d
        L_0x012a:
            r4 = r21
        L_0x012c:
            r8 = r4
        L_0x012d:
            if (r26 != 0) goto L_0x0139
            if (r14 == 0) goto L_0x0139
            if (r8 == 0) goto L_0x0139
            android.support.v4.app.Fragment r4 = r8.lastIn
            if (r4 != r10) goto L_0x0139
            r8.lastIn = r1
        L_0x0139:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p002v4.app.FragmentTransition.addToFirstInLastOut(android.support.v4.app.BackStackRecord, android.support.v4.app.BackStackRecord$Op, android.util.SparseArray, boolean, boolean):void");
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition containerTransition, SparseArray<FragmentContainerTransition> transitioningFragments, int containerId) {
        if (containerTransition != null) {
            return containerTransition;
        }
        FragmentContainerTransition containerTransition2 = new FragmentContainerTransition();
        transitioningFragments.put(containerId, containerTransition2);
        return containerTransition2;
    }

    /* renamed from: android.support.v4.app.FragmentTransition$FragmentContainerTransition */
    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }
}
