/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Edited by:   Edgard Luigi Sanchez
 * Date:        March 29, 2017
 * Desc:        This android program displays a map image and the user can insert destination points.
 *              Destinations are inserted and could be connected in the beginning of the set of points,
 *              inserted in the closest destination, or inserted in between two minimized distance between
 *              two destination.
 *
 *              This program is a product of Applied CS with Android hosted by Google.
 */

package com.google.engedu.touringmusician;

import android.graphics.Canvas;
import android.graphics.Point;

import java.util.Calendar;
import java.util.Iterator;

public class CircularLinkedList implements Iterable<Point> {

    //inner class
    private class Node {
        Point point;
        Node prev, next;

        /** YOUR CODE GOES HERE **/
        Node()
        {

        }
        //overload function
        Node(Point param_point, Node prev, Node next)
        {
            this.point = param_point;
            this.prev = prev;
            this.next = next;
        }

        public Point getPoint()
        {
            return this.point;
        }
    }
    //Global Variables
    Node head = null;

    public void insertBeginning(Point p) {
        /**  YOUR CODE GOES HERE **/
        //add current point in map for the first arg

        if(head == null)
        {
            Node newNode =  new Node(p, null, null);
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        }
        else
        {
            Node prevNode = head.prev;
            Node headNode = head;
            Node newNode = new Node(p,prevNode,head);
            prevNode.next = newNode;
            headNode.prev =  newNode;
        }

    }

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y, 2) + Math.pow(from.x-to.x, 2));
    }

    public float totalDistance() {
        float total = 0;
        /**  YOUR CODE GOES HERE**/

        Node currNode = head;

        while(currNode.next != head)
        {
            total += distanceBetween(currNode.point, currNode.next.point);
            currNode = currNode.next;
        }
        System.out.println(total);
        return total;
    }

    public void insertNearest(Point p) {
        /**  YOUR CODE GOES HERE**/
        //calculate what is the nearest distance point.
        //first case, check if the head's distance

        Node currNode = head;

        if(head == null)
        {
            insertBeginning(p);
        } //if curr node's next == head then it is a node single
        else if(currNode.next == head)
        {
           insertBeginning(p);
        }
        else //there is 2 or more nodes
        {   //else case, find all the distance from new node to the rest
            //min
            Node nearestNode = head;
            currNode = head.next;
            float dist = distanceBetween(nearestNode.point,p);

            //find the nearest node
            while(currNode != head)
            {
                if( dist > distanceBetween(currNode.point,p))
                {
                    nearestNode = currNode;
                    dist = distanceBetween(currNode.point,p);
                }
                currNode = currNode.next;
            }

            Node newNode = new Node(p,nearestNode, nearestNode.next);

            nearestNode.next = newNode;
            nearestNode = nearestNode.next;
            nearestNode.prev = newNode;
        }


    }

    public void insertSmallest(Point p) {
        /** YOUR CODE GOES HERE **/

        Node currNode = head;

        if(head == null)
        {
            insertBeginning(p);
        } //if curr node's next == head then it is a node single
        else if(currNode.next == head)
        {
            insertBeginning(p);
        }
        else //there is 2 or more nodes
        {   //else case, find all the distance from new node to the rest
            //min
            Node leftNode = currNode;
            currNode = head.next;

            float distPrev = distanceBetween(leftNode.point,p);
            float distCurr = distanceBetween(currNode.point,p);

            //find the nearest node
            while(currNode != head )
            {

                if( (distPrev + distCurr) > (distanceBetween(currNode.point,p) + distanceBetween(currNode.next.point,p)))
                {
                    distPrev = distanceBetween(currNode.point,p);
                    distCurr = distanceBetween(currNode.next.point,p);
                    //contain node and the next node where to insert the point
                    leftNode  = currNode;
                }
                currNode = currNode.next;
            }

            Node newNode = new Node(p,leftNode,leftNode.next);
            leftNode.next = newNode;
            Node rightNode = leftNode.next;
            rightNode.prev = newNode;
        }
    }

    public void reset() {
        head = null;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        public CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }


}
