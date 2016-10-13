;Definiendo las variables globales 
(defglobal ?*TCP* = 6)
(defglobal ?*UDP* = 17) ;percentage
(defglobal ?*BandwidthLimit* = 80)
(defglobal ?*BandwidthLimitPort* = 80)
(defglobal ?*Block* = "block")
(defglobal ?*Allow* = "allow")
(defglobal ?*BandwithLimitIPPort* = 50 )
(defglobal ?*usage* = 0)

;Creando Variables para almacenar las propiedades de la clase SummaryIPPort_Bandwidth

(import PersistenceData.*)
(import java.lang.Double)
(import jess.*)
(defclass JessSuggestions JessSuggestions)
(deftemplate SummaryIPPort_BandWidth (declare (from-class SummaryIPPort_BandWidth)))
(deftemplate SummaryIP_BandWidth (declare (from-class SummaryIP_BandWidth)))
(deftemplate SummaryPort_BandWidth (declare (from-class SummaryPort_BandWidth)))

(defrule matchingPortHTTP
    "Compara si HTTP esta consumiendo mas de 80 porciento
    de ancho de banda"
    (SummaryPort_BandWidth
        {bdusage > ?*BandwidthLimitPort* && port == 80}
        (port ?Port)
        (protocol ?Protocol)
    )
    (SummaryIPPort_BandWidth
            {port == 80}
            (bdusage ?Bdusage)
            (ip_Dst ?Ip_Dst)
            (ip_Src ?Ip_Src)
            (timeref ?Timeref)

    )
    =>
    (if (> ?Bdusage ?*usage*) then
    (bind ?*usage* ?Bdusage)
    (add (new JessSuggestions ?Port ?*Block* ?Protocol ?Ip_Dst ?Ip_Src ?Timeref ?Bdusage))))


(defrule matchingBandwidthIP
    "Compara si alguna IP esta consumiendo mas de un 50 porciento
    de ancho de banda"
    (SummaryIP_BandWidth 
	{bdusage > ?*BandwidthLimit*}
        (ip_Dst ?IP)
        (timeref ?Timeref)
     )
    =>
    (add (new JessSuggestions ?*Block* ?IP ?Timeref)))

(defrule matchingBandwidthPort
    "Compara si algun PUERTO esta consumiendo mas de 80 porciento
    de ancho de banda"
    (SummaryPort_BandWidth
        {bdusage > ?*BandwidthLimitPort* && port != 80 }
        (port ?Port)
        (protocol ?Protocol)
        (timeref ?Timeref)
    )
    =>
    (add (new JessSuggestions ?Port ?*Block* ?Protocol ?Timeref)))


  (defrule matchingBandwithIPPort
   "Compara si alguna IP esta consumiendo mas de 50 porciento
   de ancho de bando por algun puerto en especifico"
   (SummaryIPPort_BandWidth
       {bdusage > ?*BandwithLimitIPPort* && port != 80}
       (port ?Port)
       (ip_Dst ?IP)
       (timeref ?Timeref)
       (protocol ?Protocol)
   )    
   =>
   (add (new JessSuggestions ?IP ?*Block* ?Port ?Timeref ?Protocol)))