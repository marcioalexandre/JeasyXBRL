/**
This file is part of jeasyXBRL.

jeasyXBRL is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

jeasyXBRL is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with jeasyXBRL.  If not, see <http://www.gnu.org/licenses/>.

 Copyright (C) 2015 Marcio Alexandre Pereira da Silva
 All Rights Reserved.

 * @author 	Marcio Alexandre P. da Silva
 * @email 	marcio.alexandre83@gmail.com
 * @since 	2015-10-03
 */
package jeasyxbrl.taxonomy.linkbase;

import java.util.ArrayList;

public interface XbrlLinkbaseElement {
	public ArrayList<? extends Object> getLinkbaseElementFromFile(String filename);
	public ArrayList<? extends Object> getLinkbaseElementByArc(ArrayList<? extends Object> objects,Arc arc);
}
