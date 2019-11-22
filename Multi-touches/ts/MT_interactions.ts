import {FSM} from "./FSM";
import * as transfo from "./transfo";
import {getMatrixFromElement} from "./transfo";

function multiTouch(element: HTMLElement): void {
    let pointerId_1: number, Pt1_coord_element: SVGPoint, Pt1_coord_parent: SVGPoint,
        pointerId_2: number, Pt2_coord_element: SVGPoint, Pt2_coord_parent: SVGPoint,
        originalMatrix: SVGMatrix,
        getRelevantDataFromEvent = (evt: TouchEvent): Touch => {
            for (let i = 0; i < evt.changedTouches.length; i++) {
                let touch = evt.changedTouches.item(i);
                if (touch.identifier === pointerId_1 || touch.identifier === pointerId_2) {
                    return touch;
                }
            }
            return null;
        };

    enum MT_STATES {Inactive, Translating, Rotozooming}

    let fsm = FSM.parse<MT_STATES>({
        initialState: MT_STATES.Inactive,
        states: [MT_STATES.Inactive, MT_STATES.Translating, MT_STATES.Rotozooming],
        transitions: [
            {
                from: MT_STATES.Inactive, to: MT_STATES.Translating,
                eventTargets: [element],
                eventName: ["touchstart"],
                useCapture: false,
                action: (evt: TouchEvent): boolean => {

                    // On récupère l'id du touch premier doigt
                    pointerId_1 = evt.changedTouches.item(0).identifier;
                    originalMatrix = getMatrixFromElement(element);
                    // On calcule les cordonnées du point courant
                    Pt1_coord_parent = transfo.getPoint(evt.touches[0].clientX, evt.touches[0].clientY);
                    // On calcule le point element grace à la matrice inverse
                    Pt1_coord_element = Pt1_coord_parent.matrixTransform(originalMatrix.inverse());
                    return true;
                }
            },
            {
                from: MT_STATES.Translating, to: MT_STATES.Translating,
                eventTargets: [document],
                eventName: ["touchmove"],
                useCapture: true,
                action: (evt: TouchEvent): boolean => {

                    evt.preventDefault();
                    evt.stopPropagation();
                    // On set seulement le point courant
                    Pt1_coord_parent = transfo.getPoint(evt.changedTouches[0].clientX, evt.changedTouches[0].clientY);

                    // On appelle le drag avec le nouveau point courant
                    transfo.drag(element, originalMatrix, Pt1_coord_element, Pt1_coord_parent);

                    return true;
                }
            },
            {
                from: MT_STATES.Translating,
                to: MT_STATES.Inactive,
                eventTargets: [document],
                eventName: ["touchend"],
                useCapture: true,
                action: (evt: TouchEvent): boolean => {

                    //On remet tous les variables à null
                    Pt1_coord_parent = null;
                    Pt1_coord_element = null;
                    pointerId_1 = null;
                    return true;
                }
            },
            {
                from: MT_STATES.Translating, to: MT_STATES.Rotozooming,
                eventTargets: [element],
                eventName: ["touchstart"],
                useCapture: false,
                action: (evt: TouchEvent): boolean => {
                    //Deuxième doigts, récupération de l'événement touch
                    pointerId_2 = evt.changedTouches.item(0).identifier;

                    //On set le point courant
                    Pt2_coord_parent = transfo.getPoint(evt.touches[1].clientX, evt.touches[1].clientY);

                    //On calcule le point element à partir de la matrice inverse
                    Pt2_coord_element = Pt2_coord_parent.matrixTransform(originalMatrix.inverse());
                    return true;
                }
            },
            {
                from: MT_STATES.Rotozooming, to: MT_STATES.Rotozooming,
                eventTargets: [document],
                eventName: ["touchmove"],
                useCapture: true,
                action: (evt: TouchEvent): boolean => {
                    evt.preventDefault();
                    evt.stopPropagation();

                    // On récupère les évenement pour set les coordonnées pour chaque points
                    for (let i = 0; i < evt.changedTouches.length; i++) {
                        let touch = evt.changedTouches.item(i);
                        if (touch.identifier === pointerId_2) {
                            Pt2_coord_parent = transfo.getPoint(touch.clientX, touch.clientY);
                        } else if (touch.identifier == pointerId_1){
                            Pt1_coord_parent = transfo.getPoint(touch.clientX, touch.clientY);
                        }
                    }
                    // On appelle le rotozoom avec les nouveaux points courants
                    transfo.rotozoom(element, originalMatrix, Pt1_coord_element, Pt1_coord_parent, Pt2_coord_element, Pt2_coord_parent);

                    return true;
                }
            },
            {
                from: MT_STATES.Rotozooming,
                to: MT_STATES.Translating,
                eventTargets: [document],
                eventName: ["touchend"],
                useCapture: true,
                action: (evt: TouchEvent): boolean => {


                    // Si le point 1 est relaché on le set le point 1 avec le point 2
                    if (getRelevantDataFromEvent(evt).identifier === pointerId_1) {
                        pointerId_1 = pointerId_2;
                        Pt1_coord_element = Pt2_coord_element;
                        Pt1_coord_parent = Pt2_coord_parent;
                    }
                    // On set tous les points à null
                    pointerId_2 = null;
                    Pt2_coord_parent = null;
                    Pt2_coord_element = null;
                    originalMatrix = transfo.getMatrixFromElement(element);
                    return true;
                }
            }
        ]
    });
    fsm.start();
}

//______________________________________________________________________________________________________________________
//______________________________________________________________________________________________________________________
//______________________________________________________________________________________________________________________
function isString(s: any): boolean {
    return typeof (s) === "string" || s instanceof String;
}

export let $ = (sel: string | Element | Element[]): void => {
    let L: Element[] = [];
    if (isString(sel)) {
        L = Array.from(document.querySelectorAll(<string>sel));
    } else if (sel instanceof Element) {
        L.push(sel);
    } else if (sel instanceof Array) {
        L = sel;
    }
    L.forEach(multiTouch);
};
